package co.simplon.dev2dev_business.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.dev2dev_business.dtos.AccountCreateDto;
import co.simplon.dev2dev_business.dtos.AccountLoginDto;
import co.simplon.dev2dev_business.dtos.LoginResponseDto;
import co.simplon.dev2dev_business.dtos.VerificationLoginDto;
import co.simplon.dev2dev_business.services.AccountService;
import co.simplon.dev2dev_business.services.EmailSender;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final EmailSender emailSender;

    public AccountController(AccountService accountService, EmailSender emailSender) {
	this.accountService = accountService;
	this.emailSender = emailSender;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody AccountCreateDto accountCreateDto) {
	try {
	    accountService.create(accountCreateDto);
	    String token = UUID.randomUUID().toString();
	    return "Account created successfully !";
	} catch (Exception exception) {
	    return "Error creating account : " + exception.getMessage();
	}
    }

    @GetMapping("/verify")
    public void verifyAccount(@RequestParam String token, HttpServletResponse response) throws IOException {
	try {
	    accountService.validateAccount(token);

	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("<script>");
	    out.println("window.location.href = 'http://localhost:5173/?validation=success';");
	    out.println("</script>");
	    out.flush();
	} catch (Exception e) {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("<script>");
	    out.println("window.location.href = 'http://localhost:5173/?validation=error';");
	    out.println("</script>");
	    out.flush();
	}
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Object authentificated(@RequestBody AccountLoginDto inputs) {
	return accountService.loginResponseDto(inputs);
    }

    @PostMapping("/verification-code")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LoginResponseDto verifyCode(@RequestBody VerificationLoginDto dto) {
	return accountService.login2fa(dto);
    }
    @GetMapping("/notification-type")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> getAccountNotificationType() {
        return accountService.getAccountNotificationType();
    }

    @PatchMapping("/notification-type")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> updateAccountNotificationType(@RequestBody Map<String, Boolean> userSettings) {
        return accountService.updateAccountNotificationType(userSettings);
    }

}
