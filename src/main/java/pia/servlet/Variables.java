package pia.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Variables extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private int varCount = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Class variable
        this.varCount++;

        // Request
        int reqCount = 0;
        if (request.getAttribute("reqCount") != null) {
            reqCount = Integer.valueOf(request.getAttribute("reqCount").toString());
        }
        reqCount++;
        request.setAttribute("reqCount", reqCount);

        // Session
        int sesCount = 0;
        if (request.getSession().getAttribute("sesCount") != null) {
            sesCount = Integer.valueOf(request.getSession().getAttribute("sesCount").toString());
        }
        sesCount++;
        request.getSession().setAttribute("sesCount", sesCount);

        // Application
        int appCount = 0;
        if (getServletContext().getAttribute("appCount") != null) {
            appCount = Integer.valueOf(getServletContext().getAttribute("appCount").toString());
        }
        appCount++;
        getServletContext().setAttribute("appCount", appCount);

        // Init param
        String init = getServletContext().getInitParameter("university");

        PrintWriter out = response.getWriter();
        out.println("<pre>");
        out.println("Request: " + reqCount); // Just request
        out.println("Session: " + sesCount); // One user session
        out.println("Variable: " + this.varCount); // This class till garbage
        // collector removes it
        out.println("Application: " + appCount); // Through whole application
        out.println("Init param: " + init); // Permanent, read-only
        out.println("</pre>");

        out.flush();
        out.close();
    }
}
