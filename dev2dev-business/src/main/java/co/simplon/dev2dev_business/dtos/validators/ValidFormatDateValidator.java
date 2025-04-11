package co.simplon.dev2dev_business.dtos.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.OffsetDateTime;

public class ValidFormatDateValidator implements ConstraintValidator<ValidFormatDate, OffsetDateTime> {

    @Override
    public boolean isValid(OffsetDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.isAfter(OffsetDateTime.now())) {
            return false;
        }

        return true;
    }
}
