package co.simplon.dev2dev_business.dtos.validators;

import co.simplon.dev2dev_business.services.ArticleService;
import co.simplon.dev2dev_business.services.ProviderService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueLinkValidator implements ConstraintValidator<UniqueLink, String> {
    private final ArticleService service;
    private final ProviderService serviceProvider;

    public UniqueLinkValidator(ArticleService service, ProviderService serviceProvider) {
        this.service = service;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        System.out.println("value=" + value);
        if (value == null){
            return true;
        }
        return !service.existsByLink(value) || !serviceProvider.existsByLink(value);
    }
}
