package co.simplon.dev2dev_business.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.entities.VerificationToken;
import co.simplon.dev2dev_business.repositories.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class EmailVerificationService {
    private EmailSender emailSender;
    private final VerificationTokenRepository repository;

    protected EmailVerificationService(EmailSender emailSender, VerificationTokenRepository repository) {
	this.emailSender = emailSender;
	this.repository = repository;
    }

    public String generateVerificationCode() {
	return String.format("%04d", new Random().nextInt(10000));
    }

    @Async
    @Transactional
    public void sendVerificationCode(Account account) {

	repository.deleteByAccount(account);

	String code = generateVerificationCode();
	LocalDateTime expiration = LocalDateTime.now().plusMinutes(10);

	VerificationToken token = new VerificationToken();
	token.setAccount(account);
	token.setCodePin(code);
	token.setVerificationTime(expiration);
	repository.save(token);

	String htmlBody = load2FATemplate(code);

	try {
	    emailSender.sendEmail(account.getUsername(), "Verification Code", htmlBody);
	} catch (MessagingException e) {
	    e.printStackTrace();
	}
    }

    @Transactional
    public boolean verifyCode(Account account, String enteredCode) {
	VerificationToken token = repository.findByAccount(account)
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Code not found"));

	if (token.getVerificationTime().isBefore(LocalDateTime.now())) {
	    repository.delete(token);
	    throw new ResponseStatusException(HttpStatus.GONE, "Code has expired.");
	}

	if (!token.getCodePin().equals(enteredCode)) {
	    return false;
	}

	repository.delete(token);
	return true;
    }

    private String load2FATemplate(String code) {
	try (InputStream inputStream = getClass().getClassLoader()
		.getResourceAsStream("templates/verification-code.html")) {
	    if (inputStream == null) {
		throw new IllegalArgumentException("Template not found");
	    }
	    String template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
	    return template.replace("${code}", code);
	} catch (IOException exception) {
	    throw new RuntimeException("Failed to load 2FA email template", exception);
	}
    }

}
