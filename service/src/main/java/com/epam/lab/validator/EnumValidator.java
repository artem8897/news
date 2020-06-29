package com.epam.lab.validator;

import com.epam.lab.criteria.SortType;
import com.epam.lab.validator.annotations.IsEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<IsEnum, String> {

    @Override
    public void initialize(IsEnum isEnum) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || SortType.isExist(value);
    }
}
