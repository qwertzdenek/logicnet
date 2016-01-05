package pia.servlet;

import org.apache.openejb.loader.SystemInstance;
import org.apache.openejb.spi.ContainerSystem;
import pia.dao.AccountDao;
import pia.dao.GenericDao;
import pia.dao.JPADAO;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Date: 7.11.14
 *
 * @author Zdeněk Janeček
 */
public class IndexServlet extends HttpServlet {
    @Inject
    @JPADAO
    private AccountDao ad;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello");
    }
}
