package co.simplon.dev2dev_business.dtos.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueTitleValidator.class)
public @interface UniqueTitle {
    String message() default "The website name provided should be unique ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
