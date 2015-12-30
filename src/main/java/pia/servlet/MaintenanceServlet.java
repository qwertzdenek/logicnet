package pia.servlet;

import pia.beans.MaintenanceBean;
import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Date;

public class MaintenanceServlet extends HttpServlet {
    @Inject
    MaintenanceBean mb;

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
