package by.topolev.contacts.servlets.utils.validation.validators;

import by.topolev.contacts.servlets.utils.validation.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by Vladimir on 02.10.2016.
 */
public class IsEmailValidator implements Validator {

    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public boolean test(String value) {
        if (StringUtils.isEmpty(value)) return true;
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(value).matches();
    }

    @Override
    public String getMessageError() {
        return "Invalid email";
    }
}
