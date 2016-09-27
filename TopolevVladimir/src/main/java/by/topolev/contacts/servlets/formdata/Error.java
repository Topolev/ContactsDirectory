package by.topolev.contacts.servlets.formdata;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir on 27.09.2016.
 */
public class Error {
    private List<String> errors = new ArrayList<>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String error){
        errors.add(error);
    }
    public boolean isValid(){
        return CollectionUtils.isEmpty(errors);
    }

}
