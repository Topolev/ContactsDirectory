package by.topolev.contacts.servlets.utils.validation.validators;

import by.topolev.contacts.servlets.utils.validation.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Vladimir on 02.10.2016.
 */
public class IsNotEmptyValidator implements Validator {
    @Override
    public boolean test(String value) {
        return StringUtils.isNotEmpty(value);
    }

    @Override
    public String getMessageError() {
        return "This field could not be empty.";
    }
}
