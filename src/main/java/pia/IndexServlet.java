package pia;

import pia.dao.GenericDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
        ServletContext context = getServletContext();

        Account newAccount = new Account();
        newAccount.setPassword("testik");
        newAccount.setNickname("test");
        newAccount.setProfilePicture("img.png");
        newAccount.setBirthday(new Date(System.currentTimeMillis()));
        ad.getTransaction().begin();
        ad.save(newAccount);
        ad.getTransaction().commit();

        resp.getWriter().print(newAccount.toString());

        /*
        File image = null;
        FileInputStream in = null;

        String img = req.getParameter("img");

        if (img == null)
            img = "ntis.jpg";

        try {
            image = new File(context.getRealPath("WEB-INF/img/" + img));
            in = new FileInputStream(image);
        } catch (FileNotFoundException e) {
            context.log("Message", e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Message");
            return;
        }

        OutputStream out = resp.getOutputStream();
        resp.setContentType(context.getMimeType(image.getAbsolutePath()));
        resp.setContentLength((int) image.length());
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = in.read(buffer)) >= 0) {
            out.write(buffer, 0, count);
        }
        in.close();
        out.flush();
        out.close();
        */
    }
}
