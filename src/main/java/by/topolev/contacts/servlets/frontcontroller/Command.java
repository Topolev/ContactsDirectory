package by.topolev.contacts.servlets.frontcontroller;

import com.sun.deploy.net.HttpResponse;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Vladimir on 17.09.2016.
 */
public interface Command {
    String excute(HttpServletRequest req, HttpServletResponse resp);
}
