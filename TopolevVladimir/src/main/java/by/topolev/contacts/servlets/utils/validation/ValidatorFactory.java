package by.topolev.contacts.servlets.utils.validation;

import by.topolev.contacts.servlets.utils.validation.TypeValidator;
import by.topolev.contacts.servlets.utils.validation.validators.IsDate;
import by.topolev.contacts.servlets.utils.validation.validators.IsEmailValidator;
import by.topolev.contacts.servlets.utils.validation.validators.IsNotEmptyValidator;
import by.topolev.contacts.servlets.utils.validation.validators.IsNumberValidator;

/**
 * Created by Vladimir on 02.10.2016.
 */
public class ValidatorFactory {
    private static Validator isEmailValidator = new IsEmailValidator();
    private static Validator isNotEmptyValidator = new IsNotEmptyValidator();
    private static Validator isNumberValidator = new IsNumberValidator();
    private static Validator isDate = new IsDate();


    public static Validator getValidator(TypeValidator typeValidator) {
        switch (typeValidator) {
            case IS_NOT_EMPTY:
                return isNotEmptyValidator;
            case IS_EMAIL:
                return isEmailValidator;
            case IS_NUMBER:
                return isNumberValidator;
            case IS_DATE:
                return isDate;
        }
        return null;
    }
}
