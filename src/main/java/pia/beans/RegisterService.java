package pia.beans;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.openejb.util.Base64;
import pia.ServiceResult;
import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;
import pia.rest.entities.AccountEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;

@Stateless
public class RegisterService implements Serializable {
    @Inject
    @JPADAO
    private AccountDao ad;

    @EJB
    private ImageService imageService;

    private ServiceResult doRegister(AccountEntity account, InputStream image, String imageName) throws IOException {
        Account newAccount = new Account();
        newAccount.setId(account.getNickname());
        newAccount.setName(account.getReal_name());

        // Birthday
        Date parsedDate;
        try {
            parsedDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(account.getBirthday()).getTime());
        } catch (ParseException e) {
            return new ServiceResult("Birthday cannot be parsed. Format is yyyy-MM-dd.", false);
        }

        newAccount.setBirthday(parsedDate);

        newAccount.setProfilePicture(imageName);
        imageService.store(imageName, image);

        Set<String> roles = new LinkedHashSet<>();
        roles.add("user");

        newAccount.setRoles(roles);

        String plainPassword = account.getPassword();
        String digestedPassword;

        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            final byte[] data = md.digest(plainPassword.getBytes());

            digestedPassword = new String(Base64.encodeBase64(data));
        } catch (NoSuchAlgorithmException e){
            return new ServiceResult("Invalid machine! ;)", false);
        }

        newAccount.setPassword(digestedPassword);

        ad.save(newAccount);

        return new ServiceResult(null, true);
    }

    /**
     * JAX-RS registration
     * @param account
     * @param image
     * @return
     */
    public ServiceResult register(AccountEntity account, Attachment image) {
        // Profile picture
        String fileName = imageService.generateName(image.getContentType().toString());

        if (fileName == null) {
            return new ServiceResult("Only PNG and JPEG is supported.", false);
        }

        try (InputStream file = image.getDataHandler().getInputStream()) {
            return doRegister(account, file, fileName);
        } catch (IOException e) {
            return new ServiceResult("Image I/O error: "+e.getMessage(), false);
        }
    }

    /**
     * JSF registration
     * @param account
     * @param image
     * @return
     */
    public ServiceResult register(AccountEntity account, Part image) {
        // Profile picture
        String fileName = imageService.generateName(image.getContentType());

        if (fileName == null) {
            return new ServiceResult("Only PNG and JPEG is supported.", false);
        }

        try (InputStream file = image.getInputStream()) {
            return doRegister(account, file, fileName);
        } catch (IOException e) {
            return new ServiceResult("Image I/O error: "+e.getMessage(), false);
        }
    }
}
