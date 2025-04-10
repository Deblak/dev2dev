package co.simplon.dev2dev_business.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.dev2dev_business.dtos.AccountCreateDto;
import co.simplon.dev2dev_business.dtos.AccountLoginDto;
import co.simplon.dev2dev_business.services.AccountService;
import jakarta.validation.Valid;

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

}
