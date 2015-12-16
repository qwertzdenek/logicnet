package pia.jetty;


import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.jaas.spi.AbstractLoginModule;
import org.eclipse.jetty.util.security.Password;

import java.util.ArrayList;
import java.util.List;

public class SimpleLoginService extends AbstractLoginModule {
    @Override
    public UserInfo getUserInfo(String name) throws Exception {
        List<String> roles = new ArrayList<>(1);
        roles.add("user");
        return new UserInfo(name, new Password("strong"), roles);
    }
}
