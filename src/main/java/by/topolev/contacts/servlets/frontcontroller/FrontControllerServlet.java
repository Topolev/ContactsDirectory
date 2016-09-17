package by.topolev.contacts.servlets.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vladimir on 17.09.2016.
 */
public class FrontControllerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String page;

        RequestHelper helper = new RequestHelper(req);
        Command command = helper.getCommand();

        page = command.excute(req, resp);

        dispatch(req, resp, page);

    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        processRequest(req,resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        processRequest(req,resp);
    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws  ServletException, IOException {
        /*request.getRequestDispatcher(page).forward(request, response);*/

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
