package com.epam.lab.validator;

import com.epam.lab.dto.entity.AuthorDto;
import com.epam.lab.validator.annotations.Author;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthorValidator implements ConstraintValidator<Author, AuthorDto> {

    private static final String NAME_REG_EX = "[A-Za-z]{3,30}";

    @Override
    public void initialize(Author author) {

    }

    /**

     * Validate author

     * @param author

     * @return true if author is null or fields are valid

     */

    public boolean isValid(AuthorDto author, ConstraintValidatorContext context){
        return author != null && isAuthorFieldsValid(author);
    }

    /**

     * Validate authors name and surname

     * @param author

     * @return true if author name and surname are valid

     */

    private boolean isAuthorFieldsValid(AuthorDto author){
        return author.getName() != null && isAuthorNameValid(author.getName()) &&
                author.getSurname() != null && isAuthorNameValid(author.getSurname());
    }

    /**

     * Validate name or surname for author

     * @param name

     * @return true if name/surname less than 30 symbols and more than 3

     */

    private boolean isAuthorNameValid(String name){
        Pattern pat = Pattern.compile(NAME_REG_EX);
        Matcher matcher = pat.matcher(name);
        return matcher.matches();
    }

}
