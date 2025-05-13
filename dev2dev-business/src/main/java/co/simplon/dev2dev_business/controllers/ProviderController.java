package co.simplon.dev2dev_business.controllers;


import co.simplon.dev2dev_business.dtos.AutomatProviderUrl;
import co.simplon.dev2dev_business.dtos.ProviderCreationBodyDto;
import co.simplon.dev2dev_business.services.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("sandbox-rss/api/v1/provider")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService =providerService;
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createNewProvider(@Valid @RequestBody ProviderCreationBodyDto body) throws IOException {
        providerService.create(body);
    }

    @PatchMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void updateProvider(@Valid @RequestBody AutomatProviderUrl body) {
        providerService.testJob(body);
    }
}
