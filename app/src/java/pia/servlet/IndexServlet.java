package pia.servlet;

import pia.dao.GenericDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Date: 7.11.14
 *
 * @author Zdeněk Janeček
 */
public class IndexServlet extends HttpServlet {
    GenericDao<Account, Long> ad;

    public IndexServlet() {
        this.ad = new AccountDaoJpa();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.isNew()) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/welcome");
            dispatcher.forward(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/stream");
            dispatcher.forward(req, resp);
        }
    }
}
