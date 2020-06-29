package com.epam.lab.validator;

import com.epam.lab.validator.annotations.TagNamesList;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TagNamesValidator implements ConstraintValidator<TagNamesList, List<String>> {

    private static final String NAME_REG_EX = "[A-Za-z]{3,30}";

    @Override
    public void initialize(TagNamesList tagNamesList) {

    }

    public boolean isValid(List<String> tagNameList, ConstraintValidatorContext context){
        return tagNameList == null || tagNameList.isEmpty() || tagNameList.stream().allMatch(this::isTagValid);
    }

    private boolean isTagValid(String name){
        return name != null && isTagNameValid(name);
    }

    private boolean isTagNameValid(String tagName){
        Pattern pat = Pattern.compile(NAME_REG_EX);
        Matcher matcher = pat.matcher(tagName);
        return matcher.matches();
    }
}
