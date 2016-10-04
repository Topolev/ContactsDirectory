package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.servlets.frontcontroller.Command;
import by.topolev.contacts.servlets.utils.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.topolev.contacts.servlets.utils.ServletUtil.getRequestParameter;

/**
 * Created by Vladimir on 27.09.2016.
 */
public class ChangeLanguageCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(ChangeLanguageCommand.class);
    public static final String LAN = "lan";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String choosenLocale = getRequestParameter(req,"lan",String.class,"en");

        LOG.debug("Change language locale. Choosen local: {}", choosenLocale);

        Cookie cookie = new Cookie(LAN, choosenLocale);
        cookie.setMaxAge(24*60*60);
        resp.addCookie(cookie);
        return "/";
    }
}
