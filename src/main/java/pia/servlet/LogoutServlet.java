package pia.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ServletSecurity(value = @HttpConstraint(rolesAllowed = "user"))
public class LogoutServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.logout();

        resp.setContentType("text/plain");
        resp.setHeader("Refresh", "2; URL=/");
        resp.getWriter().write("You have been successfully logged out.");
    }
}
