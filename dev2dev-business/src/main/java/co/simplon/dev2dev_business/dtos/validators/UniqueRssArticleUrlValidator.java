package co.simplon.dev2dev_business.dtos.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueRssArticleUrlValidator implements ConstraintValidator<UniqueRssArticleUrl, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
//        System.out.println("value=" + value);
//        if(null == value){
//            return true;
//        }
    }
}
