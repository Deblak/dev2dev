package co.simplon.dev2dev_business.dtos.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFormatDateValidator.class)
public @interface ValidFormatDate {
    String message() default "The format provided is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
