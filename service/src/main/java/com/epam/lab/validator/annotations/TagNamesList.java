package com.epam.lab.validator.annotations;

import com.epam.lab.validator.TagNamesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TagNamesValidator.class)
@Documented
public @interface TagNamesList {

    String message() default "list of tags is not initialized.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
