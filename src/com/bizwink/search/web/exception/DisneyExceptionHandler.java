package com.bizwink.search.web.exception;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.StripesConstants;
import net.sourceforge.stripes.exception.ExceptionHandler;
import net.sourceforge.stripes.validation.SimpleError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhang
 * Date: 12-5-25
 * Time: 上午8:25
 * To change this template use File | Settings | File Templates.
 */
public class DisneyExceptionHandler implements ExceptionHandler {
    /** Doesn't have to do anything... */
    public void init(Configuration configuration) throws Exception { }


    /**
     * If there's an ActionBean present, send the user back where they came from with
     * a stern warning, otherwise send them to the global error page.
     */
    public void handle(Throwable throwable,
                       HttpServletRequest request,
                       HttpServletResponse response) throws ServletException  {
        ActionBean bean = (ActionBean)   request.getAttribute(StripesConstants.REQ_ATTR_ACTION_BEAN);

        if (bean != null) {
            bean.getContext().getValidationErrors().addGlobalError(
                    new SimpleError("尝试了些无效的数据!  Bad/Good user?"));

            try {
                bean.getContext().getSourcePageResolution().execute(request, response);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        else {
            try {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /** Do something a bit more complicated that just going to a view. */
   /* public void handle(Throwable throwable,
                       HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        TransactionUtil.rollback(); // rollback any tx in progress
        if (AppProperties.isDevMode()) {
            throw new StripesServletException(throwable);
        }
        else {
            request.setAttribute("exception", throwable);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    } */
}