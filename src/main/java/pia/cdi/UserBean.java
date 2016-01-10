package pia.cdi;

import pia.dao.AccountDao;
import pia.dao.FriendshipDao;
import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Account;
import pia.data.Post;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.*;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Named
@RequestScoped
public class UserBean {
    private Account account;

    @Inject
    @JPADAO
    private AccountDao ad;

    @Inject
    @JPADAO
    private PostDao pd;

    @Inject
    @JPADAO
    private FriendshipDao fd;

    @Inject
    UserTransaction t;

    @Inject
    private Principal principal;

    public String getUsername() {
        if (principal != null) {
            return getAccount().getName();
        } else {
            return null;
        }
    }

    public Account getAccount() {
        if (account == null) {
            account = ad.findOne(principal.getName());
        }
        return account;
    }

    public List<Account> getIncomingFriends() {
        return ad.fetchIncomingFriends(getAccount());
    }

    public List<Account> getFriends() {
        return fd.allFriends(getAccount());
    }

    public long getAge() {
        Date birhday = getAccount().getBirthday();
        LocalDate birthday = birhday.toLocalDate();
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Prague"));
        return ChronoUnit.YEARS.between(birthday, today);
    }

    public long getFriendCount() { return fd.friendCount(getAccount()); }

    public int getPostCount() {
        return ad.postCount(getAccount());
    }

    public int getLikesCount() {
        return ad.likesCount(getAccount());
    }

    public List<Post> getLatestPosts(Account account) {
        return pd.getLatestPosts(account, 8);
    }
}
