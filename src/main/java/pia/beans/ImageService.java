package pia.beans;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Stateless
public class ImageService {
    private static final String subdir = "images";
    private static String uploadDir;

    @Inject
    private ServletContext context;

    @PostConstruct
    public void init() {
        uploadDir = context.getInitParameter("upload-dir");

        // create image directory
        File dir = Paths.get(uploadDir, "images").toFile();
        dir.mkdir();
    }

    public String generateName(String type) {
        String extension;

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
        return UUID.randomUUID().toString() + extension;
    }

    public boolean store(String name, InputStream file) throws IOException {
        Path path = Paths.get(uploadDir, subdir, name);
        Files.copy(file, path);

        return true;
    }

    public Path getPath(String name) {
        return Paths.get(uploadDir, subdir, name);
    }

    public boolean exists(String filename) {
        return getPath(filename).toFile().exists();
    }
}
