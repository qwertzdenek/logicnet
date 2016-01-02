package pia.servlet;

import pia.beans.MaintenanceService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MaintenanceServlet extends HttpServlet {
    @Inject
    MaintenanceService mb;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mb.seed();

        resp.getWriter().write("OK\n");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // cleanup database
    }
}
