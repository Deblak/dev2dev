package co.simplon.dev2dev_business.dtos.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD) // Indique que l'annotation sera uniquement utilisée sur les champs (et non méthodes, classes etc)
@Retention(RetentionPolicy.RUNTIME) // Indique que l'annotation sera disponible à l'exécution (peut etre lu et utiliser par le systeme durant le runtime)
@Constraint(validatedBy = UniqueProviderUrlValidator.class) // Indique que la règle de gestion de validation sera implémenter dans la class indiquée (ici UniqueTitleValidator.class)
public @interface UniqueProviderUrl {
    //Le @ sert à indique que notre annotation est personnalisée Unique

    String message() default "The link must be unique and valid. Please try again ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
