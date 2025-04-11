package co.simplon.dev2dev_business.dtos.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueRssArticleUrlValidator.class)
public @interface UniqueRssArticleUrl {

    String message() default "The link must be unique. Please try again ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
