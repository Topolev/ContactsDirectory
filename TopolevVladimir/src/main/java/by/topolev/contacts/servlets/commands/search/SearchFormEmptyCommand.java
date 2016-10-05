package by.topolev.contacts.servlets.commands.search;

import by.topolev.contacts.servlets.frontcontroller.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.topolev.contacts.servlets.utils.PageNames.PAGE_SEARCH_FORM_EMPTY;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class SearchFormEmptyCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return PAGE_SEARCH_FORM_EMPTY;
    }
}
