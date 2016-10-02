package by.topolev.contacts.servlets.utils.validation.validators;

import by.topolev.contacts.servlets.utils.validation.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Vladimir on 02.10.2016.
 */
public class IsNumberValidator implements Validator {
    @Override
    public boolean test(String value) {
        if (StringUtils.isEmpty(value)) return true;
        return StringUtils.isNumeric(value);
    }

    @Override
    public String getMessageError() {
        return "This field has to consist of number only.";
    }
}
