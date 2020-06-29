package com.epam.lab.validator.annotations;

import com.epam.lab.validator.AuthorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AuthorValidator.class)
@Documented
public @interface Author {

    String message() default "author is not initialized.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
