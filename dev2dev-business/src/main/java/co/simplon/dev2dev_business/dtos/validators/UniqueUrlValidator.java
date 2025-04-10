package co.simplon.dev2dev_business.dtos.validators;

import co.simplon.dev2dev_business.services.ProviderService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUrlValidator implements ConstraintValidator<UniqueUrl,String> {

    private final ProviderService providerService;
    public UniqueUrlValidator(ProviderService providerService) {
        this.providerService = providerService;
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("value=" + value);
        if(null == value){
            return true;
        }
        return !providerService.existsByLink(value);
    }
}
