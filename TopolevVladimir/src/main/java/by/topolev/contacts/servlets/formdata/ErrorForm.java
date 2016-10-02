package by.topolev.contacts.servlets.formdata;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;
import java.util.TreeMap;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * Created by Vladimir on 02.10.2016.
 */
public class ErrorForm {
    private Map<String, String> errors = new TreeMap<>();

    public void addError(String field, String errorMessage) {
        errors.put(field, errorMessage);
    }

    public boolean isValid() {
        return isEmpty(errors.values());
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
