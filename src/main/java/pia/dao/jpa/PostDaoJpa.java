package pia.dao.jpa;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Account;
import pia.data.Post;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JPADAO
public class PostDaoJpa extends GenericDaoJpa<Post, Long> implements PostDao {
    @Inject
    @JPADAO
    AccountDao ad;

    public PostDaoJpa() {
        super(Post.class);
    }

    @Override
    public List<Post> getMostPopularDay() {
        TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p WHERE p.dateTime > :dayBefore order by p.likesCount DESC, p.dateTime DESC", Post.class);

        LocalDateTime dt = LocalDateTime.now().minusDays(1);
        q.setParameter("dayBefore", Timestamp.valueOf(dt));
        q.setMaxResults(8);

        return q.getResultList();
    }

    @Override
    public List<Post> getLastPosts() {
        TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p ORDER BY p.dateTime DESC", Post.class);

        return q.getResultList();
    }

    @Override
    public List<Post> getLatestFrom(Account account) {
        TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p WHERE p.writer = :writer ORDER BY p.dateTime DESC", Post.class);
        q.setParameter("writer", account);
        q.setMaxResults(8);
        return q.getResultList();
    }

    @Override
    public int addLike(Post post, Account who) {
        who.getLikedPosts().add(post);

        int count = post.getLikesCount()+1;
        post.setLikesCount(count);

        save(post);
        ad.save(who);

        return count;
    }
}
