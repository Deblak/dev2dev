package co.simplon.dev2dev_business.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.simplon.dev2dev_business.dtos.AccountCreateDto;
import co.simplon.dev2dev_business.dtos.AccountLoginDto;
import co.simplon.dev2dev_business.services.AccountService;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
	this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody AccountCreateDto accountCreateDto) {
	try {

	    accountService.create(accountCreateDto);

	    return "Account created successfully !";
	} catch (Exception exception) {
	    return "Error creating account : " + exception.getMessage();
	}
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    Object authentificated(@RequestBody AccountLoginDto inputs) {
	return accountService.LoginResponseDto(inputs);
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
