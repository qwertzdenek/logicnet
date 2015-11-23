package pia.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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

    public static String store(ServletContext context, Part file) {
        if (uploadDir == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        String type = file.getContentType();
        String extension;
        String subdir;

        switch (type) {
            case "image/png":
                extension = ".png";
                subdir = "images";
                break;
            case "image/jpeg":
                extension = ".jpeg";
                subdir = "images";
                break;
            default:
                return null;
        }
        String name = UUID.randomUUID().toString() + extension;
        Path path = Paths.get(uploadDir, subdir, name);
        try {
            file.write(path.toString());
        } catch (IOException e) {
            context.log("** file I/O error > " + path.toString());
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
