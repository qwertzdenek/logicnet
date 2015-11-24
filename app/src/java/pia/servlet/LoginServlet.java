package pia.servlet;

import pia.dao.GenericDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date: 7.11.14
 *
 * @author Zdeněk Janeček
 */
public class LoginServlet extends HttpServlet {
    GenericDao<Account, Long> ad;

    public LoginServlet() {
        this.ad = new AccountDaoJpa();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("content", "login.jsp");
        req.setAttribute("title", "LogicNet login screen");

        req.getRequestDispatcher("WEB-INF/html/view.jsp").forward(req, resp);
    }
}
