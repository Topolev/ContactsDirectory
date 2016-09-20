package by.topolev.contacts.servlets.frontcontroller;

import by.topolev.contacts.config.ConfigUtil;
import by.topolev.contacts.qurtz.JobsFactory;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class FrontControllerFilter implements Filter{

    private static final Logger LOG = LoggerFactory.getLogger(FrontControllerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();

        ConfigUtil.init(servletContext);
        /*
        try {
            JobsFactory.initJobs();
            LOG.debug("Initial of JOBs was successfull.");
        } catch (SchedulerException e) {
            LOG.debug("Problems with initial jobs", e);
        }*/

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
