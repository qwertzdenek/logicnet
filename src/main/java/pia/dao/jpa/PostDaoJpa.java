package pia.dao.jpa;

import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Account;
import pia.data.Post;

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
    public PostDaoJpa() {
        super(Post.class);
    }

    @Override
    public List<Post> getMostPopularDay() {
        TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p WHERE p.dateTime > :dayBefore", Post.class);

        LocalDateTime dt = LocalDateTime.now().minusDays(1);
        q.setParameter("dayBefore", Timestamp.valueOf(dt));

        return q.getResultList();
    }

    @Override
    public List<Post> getLatestFrom(Account account) {
        return null;
    }
}
