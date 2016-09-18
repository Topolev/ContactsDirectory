package by.topolev.contacts.servlets.frontcontroller;

import com.sun.deploy.net.HttpResponse;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vladimir on 17.09.2016.
 */
public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
