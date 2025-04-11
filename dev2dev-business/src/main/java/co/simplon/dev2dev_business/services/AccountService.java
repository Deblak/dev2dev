package co.simplon.dev2dev_business.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.UUID;

import co.simplon.dev2dev_business.configs.JwtHelper;
import co.simplon.dev2dev_business.entities.NotificationType;
import co.simplon.dev2dev_business.repositories.NotificationTypeRepository;
import com.nimbusds.jose.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import co.simplon.dev2dev_business.configs.JwtProvider;
import co.simplon.dev2dev_business.dtos.AccountCreateDto;
import co.simplon.dev2dev_business.dtos.AccountLoginDto;
import co.simplon.dev2dev_business.dtos.LoginResponseDto;
import co.simplon.dev2dev_business.dtos.VerificationLoginDto;
import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.entities.Role;
import co.simplon.dev2dev_business.repositories.AccountRepository;
import co.simplon.dev2dev_business.repositories.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import co.simplon.dev2dev_business.repositories.VerificationTokenRepository;
import jakarta.mail.MessagingException;

@Service
public class AccountService {

    private final AccountRepository accountsRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final EmailVerificationService email;
	private final NotificationTypeRepository notificationTypeRepository;
    private final EmailSender emailSender;
    private final EmailVerificationService emailVerificationService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${dev2dev-business.token.expiration-minutes}")
    private long tokenExpMinutes;

    @Value("${dev2dev-business.email.confirmation-url-base}")
    private String confirmationUrlBase;

    @Value("${dev2dev-business.email.verification-url-back}")
    private String verificationUrlBack;

    protected AccountService(AccountRepository accountsRepo, RoleRepository roleRepository, PasswordEncoder encoder,
                             JwtProvider jwtProvider, EmailVerificationService email, NotificationTypeRepository notificationTypeRepository, EmailSender emailSender, EmailVerificationService emailVerificationService,
                             VerificationTokenRepository verificationTokenRepository) {
	this.accountsRepo = accountsRepo;
	this.roleRepository = roleRepository;
	this.encoder = encoder;
	this.jwtProvider = jwtProvider;
        this.email = email;
        this.notificationTypeRepository = notificationTypeRepository;
        this.emailSender = emailSender;
	this.emailVerificationService = emailVerificationService;
	this.verificationTokenRepository = verificationTokenRepository;
    }

    @Transactional
    public void create(AccountCreateDto inputs) {

	if (inputs.username() == null || inputs.username().trim().isEmpty()) {
	    throw new IllegalArgumentException("Username cannot be empty.");
	}
	if (accountsRepo.findByUsernameIgnoreCase(inputs.username()).isPresent()) {
	    throw new IllegalArgumentException("The user with that name already exists.");
	}
	Account entity = new Account();
	entity.setUsername(inputs.username());
	String encodedPassword = encoder.encode(inputs.password());
	entity.setPassword(encodedPassword);

	Role role;
	role = roleRepository.findByName("MEMBER")
		.orElseThrow(() -> new RuntimeException("Default MEMBER role not found"));
	entity.setRole(role);

	String validationToken = UUID.randomUUID().toString();
	entity.setUuidToken(validationToken);
	entity.setExpirationToken(LocalDateTime.now().plusMinutes(tokenExpMinutes));

	accountsRepo.save(entity);

	String validationLink = confirmationUrlBase + "?token=" + validationToken;
	sendValidationEmail(inputs.username(), validationToken);
    }

    @Transactional
    public void validateAccount(String token) {
	Account account = accountsRepo.findByUuidToken(token)
		.orElseThrow(() -> new IllegalArgumentException("Invalid token"));
	if (account.getExpirationToken().isBefore(LocalDateTime.now())) {
	    throw new IllegalArgumentException("Token has expired");
	}
	account.setEmailValidate(true);
	accountsRepo.save(account);

    }

    public void sendValidationEmail(String email, String token) {
	String verificationUrl = verificationUrlBack + "?token=" + token;
	String body = loadTemplate("email-account-template.html", token);

	try {
	    emailSender.sendEmail(email, "[IMPORTANT] Validation account", body);
	} catch (MessagingException exception) {
	    throw new RuntimeException("Failed to send validation email", exception);
	}
    }

    private String loadTemplate(String filename, String token) {
	try {
	    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/" + filename);
	    if (inputStream == null) {
		throw new IllegalArgumentException("Template not found: " + filename);
	    }
	    String template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
	    String verificationUrl = verificationUrlBack + "?token=" + token;
	    return template.replace("${link}", verificationUrl);
	} catch (IOException exception) {
	    throw new RuntimeException("Failed to load email template", exception);
	}
    }

    public LoginResponseDto loginResponseDto(AccountLoginDto inputs) {
	String username = inputs.username();
	Account entity = accountsRepo.findByUsernameIgnoreCase(username)
		.orElseThrow(() -> new BadCredentialsException(username));

	String password = inputs.password();
	String encoded = entity.getPassword();

	if (!encoder.matches(password, encoded)) {
	    throw new BadCredentialsException(username);
	}

	if (!entity.isEmailValidate()) {
	    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Email is not verified.");
	}

	emailVerificationService.sendVerificationCode(entity);

	return new LoginResponseDto(null, "Authentication need a second verification", entity.getRole().getName());
    }

    public LoginResponseDto login2fa(VerificationLoginDto dto) {
	String username = dto.username();
	Account account = accountsRepo.findByUsernameIgnoreCase(username)
		.orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

	boolean isValid = emailVerificationService.verifyCode(account, dto.code());
	if (!isValid) {
	    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid or expired verification code.");
	}

	String roleName = account.getRole().getName();
	String token = jwtProvider.create(username, Set.of(roleName));

	return new LoginResponseDto(token, "Login successful", roleName);
    }

	@Transactional
	public Map<String, Boolean> updateAccountNotificationType(Map<String, Boolean> userSettings) {
		String accountEmail = JwtHelper.getSubject();
		Account account = accountsRepo.findByUsernameIgnoreCase(accountEmail)
				.orElseThrow(() -> new BadCredentialsException(String.format("Account with username: %s not found !", accountEmail)));
		List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
		userSettings.keySet().forEach(key -> {
            if (userSettings.get(key)) {
                account.addNotificationType(notificationTypeList.stream().filter(n -> n.getTypeName().equals(key)).toList().getFirst());
            } else {
                account.removeNotificationType(notificationTypeList.stream().filter(n -> n.getTypeName().equals(key)).toList().getFirst());
            }
        });
		Account updatedAccount = accountsRepo.saveAndFlush(account);
		Map<String, Boolean> notificationAccountSetting = new HashMap<>();
		notificationTypeList.forEach( notificationType ->
				notificationAccountSetting.put(notificationType.getTypeName(), updatedAccount.getNotificationTypeSet().stream().map(NotificationType::getTypeName).toList().contains(notificationType.getTypeName())));
		return notificationAccountSetting;
	}

	public Map<String, Boolean> getAccountNotificationType() {
		String accountEmail = JwtHelper.getSubject();
		Account account = accountsRepo.findByUsernameIgnoreCase(accountEmail)
				.orElseThrow(() -> new BadCredentialsException(String.format("Account with username: %s not found !", accountEmail)));
		List<NotificationType> notificationTypeSet = notificationTypeRepository.findAll();
		Map<String, Boolean> notificationAccountSetting = new HashMap<>();
		notificationTypeSet.forEach( notificationType ->
				notificationAccountSetting.put(notificationType.getTypeName(), account.getNotificationTypeSet().stream().map(NotificationType::getTypeName).toList().contains(notificationType.getTypeName())));
		return notificationAccountSetting;
	}
}
