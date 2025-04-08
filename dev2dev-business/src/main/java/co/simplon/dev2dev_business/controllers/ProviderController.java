package co.simplon.dev2dev_business.controllers;


import co.simplon.dev2dev_business.dtos.ProviderDto;
import co.simplon.dev2dev_business.services.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sandbox-rss/api/v1/provider")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService =providerService;
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createNewProvider( @Valid @RequestBody ProviderDto body) {
        providerService.create(body);
    }
}
