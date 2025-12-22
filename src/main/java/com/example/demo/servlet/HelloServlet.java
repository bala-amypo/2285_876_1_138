package com.example.demo.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "9057.pro604cr.amypo.ai")
public class HelloServlet extends HttpServlet {
    private String message;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.message = "Hello Tomcat";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Hello from servlet");
    }

    public String getMessage() {
        return message;
    }
}