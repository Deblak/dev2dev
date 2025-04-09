package co.simplon.dev2dev_business.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.dev2dev_business.dtos.AccountLoginDto;
import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService service;

    protected LoginController(LoginService service) {
	this.service = service;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    Object authentificated(@RequestBody AccountLoginDto inputs) {
	return service.LoginResponseDto(inputs);
    }

    @GetMapping("/display-last")
    public Account entitiesDisplay(Long id) {
	return service.accountInfo();
    }
}
