package pia.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by janecekz on 3.11.2015.
 */
public class FormGet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String nameGet = request.getParameter("name");
        String lastnameGet = request.getParameter("lastname");

        getServletContext().log("GET name: " + nameGet + " lastname: " + lastnameGet);
        response.sendRedirect("Form.html");
    }
}
