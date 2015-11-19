package pia;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Date: 7.11.14
 *
 * @author Zdeněk Janeček
 */
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();

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
    }
}