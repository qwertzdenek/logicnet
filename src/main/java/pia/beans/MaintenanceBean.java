package pia.beans;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.sql.Date;

@Stateful
public class MaintenanceBean {
    @Inject
    @JPADAO
    private AccountDao ad;

    public void seed() {
        Account root = new Account();
        root.setBirthday(new Date(0));
        root.setId("root");
        root.setName("Zdeněk Janeček");
        root.setPassword("strong");
        root.setProfilePicture("neco.png");
        root.addRole("user");

        ad.store(root);
    }
}
