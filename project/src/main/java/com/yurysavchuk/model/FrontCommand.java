package com.yurysavchuk.model;


import com.yurysavchuk.dao.mysql.MySqlAddressDaoImpl;
import com.yurysavchuk.dao.mysql.MySqlContactDaoImpl;
import com.yurysavchuk.dao.mysql.MySqlFilesDaoImpl;
import com.yurysavchuk.dao.mysql.MySqlNumbersDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


abstract public class FrontCommand {

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected MySqlAddressDaoImpl addressDao = new MySqlAddressDaoImpl();
    protected MySqlContactDaoImpl contactDao = new MySqlContactDaoImpl();
    protected MySqlFilesDaoImpl filesDao = new MySqlFilesDaoImpl();
    protected MySqlNumbersDaoImpl numbersDao = new MySqlNumbersDaoImpl();

    public void init(ServletContext context,
                     HttpServletRequest request,
                     HttpServletResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
    }

    abstract public void process() throws ServletException, IOException;

    protected void forward(String target) throws ServletException, IOException {
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }
}
