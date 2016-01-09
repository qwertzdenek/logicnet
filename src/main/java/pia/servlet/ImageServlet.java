package pia.servlet;

import pia.beans.ImageService;
import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

public class ImageServlet extends HttpServlet {
    @EJB
    ImageService imageService;

    @Inject
    ServletContext context;

    @Inject
    Principal principal;

    @Inject
    @JPADAO
    AccountDao ad;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getPathInfo().substring(1);

        if (!imageService.exists(filename)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (principal == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        File file = imageService.getPath(filename).toFile();
        resp.setContentType(context.getMimeType(filename));
        resp.setContentLength((int) file.length());
        resp.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
