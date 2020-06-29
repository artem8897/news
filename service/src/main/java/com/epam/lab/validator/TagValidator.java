package com.epam.lab.validator;

import com.epam.lab.dto.entity.TagDto;
import com.epam.lab.validator.annotations.TagDtoList;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component

public class TagValidator implements ConstraintValidator<TagDtoList, Set<TagDto>> {

    private static final String NAME_REG_EX = "[A-Za-z]{3,30}";

    @Override
    public void initialize(TagDtoList tagDtoList) {

    }

    /**

     * Validate tag

     * @param tags

     * @return true if tag is not null and name us valid

     */

    public boolean isValid(Set<TagDto> tags, ConstraintValidatorContext context){
        return tags == null || tags.isEmpty() || tags.stream().allMatch(this::isTagValid);
    }

    /**

     * Validate tag

     * @param tag

     * @return true if tag is not null and name is valid

     */
    private boolean isTagValid(TagDto tag){
        return tag != null && tag.getName() != null && isTagNameValid(tag.getName());
    }

    /**

     * Validate tag name for tag

     * @param tagName

     * @return true if tag name less than 30 symbols and more than 3

     */

    private boolean isTagNameValid(String tagName){

        Pattern pat = Pattern.compile(NAME_REG_EX);
        Matcher matcher = pat.matcher(tagName);
        return matcher.matches();
    }

}
