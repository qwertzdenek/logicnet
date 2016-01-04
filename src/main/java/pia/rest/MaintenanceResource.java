package pia.rest;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.time.Instant;
import java.util.Date;

@Stateless
@Path("service")
public class MaintenanceResource {
    @Inject
    @JPADAO
    private AccountDao ad;

    @POST
    @Path("seed")
    public void seed() {
        Account root = new Account();
        root.setBirthday(Date.from(Instant.now()));
        root.setId("root");
        root.setName("Zdeněk Janeček");
        root.setPassword("strong");
        root.setProfilePicture("neco.png");
        root.addRole("user");

        ad.store(root);
    }
}
