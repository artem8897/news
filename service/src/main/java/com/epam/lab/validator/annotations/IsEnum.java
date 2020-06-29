package com.epam.lab.validator.annotations;

import com.epam.lab.validator.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
@Documented
public @interface IsEnum {

        String message() default "sort is not allowed.";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};


}
