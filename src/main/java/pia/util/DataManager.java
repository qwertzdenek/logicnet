package pia.util;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// TODO: do it as EJB

@WebListener
public class DataManager implements ServletContextListener {
    private static String uploadDir;

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        uploadDir = context.getInitParameter("upload-dir");

        // create image directory
        File dir = Paths.get(uploadDir, "images").toFile();
        dir.mkdir();

        context.log("** DataManager initialized");
    }

    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.log("** DataManager closing");
    }

    public static String store(Attachment file) {
        if (uploadDir == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        String type = file.getContentType().toString();
        String extension;
        String subdir = "images";

        switch (type) {
            case "image/png":
                extension = ".png";
                break;
            case "image/jpeg":
                extension = ".jpeg";
                break;
            default:
                return null;
        }
        String name = UUID.randomUUID().toString() + extension;
        Path path = Paths.get(uploadDir, subdir, name);

        try {
            InputStream in = file.getDataHandler().getInputStream();
            FileOutputStream out = new FileOutputStream(path.toFile());
            IOUtils.copyAndCloseInput(in, out);
            out.close();
        } catch (IOException e) {
            return null;
        }

        return name;
    }

    public static void load(ServletContext context, String name) {
        if (uploadDir == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        // TODO
    }
}
