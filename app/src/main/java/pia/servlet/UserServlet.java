package pia.servlet;

import pia.dao.GenericDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;
import pia.util.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

/**
 * Date: 7.11.14
 *
 * @author Zdeněk Janeček
 */
@Deprecated
public class UserServlet extends HttpServlet {
    GenericDao<Account, Long> ad;

    public UserServlet() {
        this.ad = new AccountDaoJpa(DBManager.createEntityManager());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        HttpSession session = req.getSession();

        Account newAccount = new Account();
        newAccount.setPassword("testik");
        newAccount.setNickname("test");
        newAccount.setProfilePicture("img.png");
        newAccount.setBirthday(new Date(System.currentTimeMillis()));
        ad.getTransaction().begin();
        ad.save(newAccount);
        ad.getTransaction().commit();

        req.setAttribute("user", newAccount);

        RequestDispatcher dispatcher = req.getRequestDispatcher("view");
        dispatcher.forward(req, resp);
    }
}
