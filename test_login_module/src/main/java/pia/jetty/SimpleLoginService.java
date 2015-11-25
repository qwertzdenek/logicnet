package pia.jetty;


import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.jaas.spi.AbstractLoginModule;
import org.eclipse.jetty.util.security.Password;
import pia.data.Account;
import pia.util.DBManager;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class SimpleLoginService extends AbstractLoginModule {
    public SimpleLoginService() {
        EntityManager em = DBManager.createEntityManager();
    }

    @Override
    public UserInfo getUserInfo(String name) throws Exception {
        List<String> roles = new ArrayList<String>(1);
        roles.add("user");
        return new UserInfo(name, new Password("strong"), roles);
    }
}
