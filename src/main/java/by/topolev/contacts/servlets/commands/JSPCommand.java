package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.servlets.frontcontroller.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Vladimir on 17.09.2016.
 */
public class JSPCommand implements Command {

    private String nameJSP;

    public JSPCommand(String nameJSP){
        this.nameJSP = nameJSP;
    }

    @Override
    public String excute(HttpServletRequest req, HttpServletResponse resp) {
        return nameJSP;
    }
}
