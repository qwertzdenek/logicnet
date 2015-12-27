package pia.servlet;

import pia.dao.GenericDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;
import pia.util.DBManager;

import javax.inject.Inject;
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
public class IndexServlet extends HttpServlet {
    GenericDao<Account, Long> ad;

    public IndexServlet() {
        this.ad = new AccountDaoJpa(DBManager.createEntityManager());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: gather user informations

        resp.getWriter().write("jsem tu");
    }
}
