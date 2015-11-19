package pia.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by janecekz on 3.11.2015.
 */
public class FormPost extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String namePost = request.getParameter("name");
        String lastnamePost = request.getParameter("lastname");

        getServletContext().log("POST name: " + namePost + " lastname: " + lastnamePost);
        response.sendRedirect("Form.html");
    }
}
