package co.simplon.dev2dev_business.dtos.validators;

import co.simplon.dev2dev_business.services.ArticleService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueLinkValidator implements ConstraintValidator<UniqueLink, String> {
    private final ArticleService service;

    public UniqueLinkValidator(ArticleService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        //System.out.println("value=" + value);
        if (value == null){
            return true;
        }
        return !service.existsByUrl(value);
    }
}
