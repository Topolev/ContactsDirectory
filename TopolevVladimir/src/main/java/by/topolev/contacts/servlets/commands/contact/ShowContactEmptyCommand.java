package by.topolev.contacts.servlets.commands.contact;

import by.topolev.contacts.servlets.frontcontroller.Command;
import by.topolev.contacts.servlets.utils.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.topolev.contacts.servlets.utils.PageNames.PAGE_CONTACT;
import static by.topolev.contacts.servlets.utils.ServletUtil.getRequestParameter;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class ShowContactEmptyCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return PAGE_CONTACT;
    }
}
