package co.simplon.dev2dev_business.dtos.validators;

import co.simplon.dev2dev_business.services.ProviderService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//ConstraintValidator<UniqueTitle, String> : Cela signifie que cette classe va valider des objets annotés avec l'annotation @UniqueTitle (comme dans ton précédent code). Elle va aussi valider des champs de type String (comme les Title dans cet exemple).
public class UniqueTitleValidator implements ConstraintValidator<UniqueTitle, String> {

    private final ProviderService providerService;

    public UniqueTitleValidator(ProviderService providerService) {
        this.providerService = providerService;
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("value=" + value);
        if(null == value){
            return true;
        }
        return !providerService.existsByTitle(value);
    }
}
