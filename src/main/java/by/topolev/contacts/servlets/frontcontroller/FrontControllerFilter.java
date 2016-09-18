package by.topolev.contacts.servlets.frontcontroller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class FrontControllerFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        RequestHelper.init(servletContext);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        dispatch((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    protected void dispatch(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req.getRequestURI().endsWith("\\.jsp")){
            chain.doFilter(req, resp);
        } else{
            RequestHelper helper = new RequestHelper(req);
            Command command = helper.getCommand();

            if (command != null){
                String page = command.execute(req, resp);
                if (page != null) {
                    RequestDispatcher dispatcher = req.getRequestDispatcher(page);
                    dispatcher.forward(req, resp);
                }
            } else{
                chain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
