package co.simplon.dev2dev_business.dtos.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = UniqueAccountValidator.class)
public @interface UniqueAccount {
	String message() default "The username is already exits";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
