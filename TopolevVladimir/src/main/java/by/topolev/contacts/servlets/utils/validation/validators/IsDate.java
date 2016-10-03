package by.topolev.contacts.servlets.utils.validation.validators;

import by.topolev.contacts.servlets.utils.validation.Validator;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Vladimir on 03.10.2016.
 */
public class IsDate implements Validator {
    @Override
    public boolean test(String value) {
        System.out.println("Attention: " + value);
        if (StringUtils.isEmpty(value)) return true;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String getMessageError() {
        return "Date is invalid";
    }
}
