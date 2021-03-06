package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.servlets.frontcontroller.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.topolev.contacts.servlets.utils.PageNames.PAGE_INDEX;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class IndexCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return PAGE_INDEX;
    }
}
