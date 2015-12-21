package pia.servlet;

import org.eclipse.jetty.util.log.Log;
import pia.dao.AccountDao;
import pia.dao.RoleDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.dao.jpa.RoleDaoJpa;
import pia.data.Account;
import pia.data.Role;
import pia.util.DBManager;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 7.11.14
 *
 * @author Zdeněk Janeček
 */
public class WelcomeServlet extends HttpServlet {
    AccountDao ad;
    RoleDao rd;

    public WelcomeServlet() {
        EntityManager em = DBManager.createEntityManager();
        this.ad = new AccountDaoJpa(em);
        this.rd = new RoleDaoJpa(em);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("content", "welcome.jsp");
        req.setAttribute("title", "LogicNet welcome screen");

        req.getRequestDispatcher("WEB-INF/html/view.jsp").forward(req, resp);
    }
}
