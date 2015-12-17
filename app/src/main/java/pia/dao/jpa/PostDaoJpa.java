package pia.dao.jpa;

import pia.dao.PostDao;
import pia.data.Post;

import javax.persistence.EntityManager;

public class PostDaoJpa extends GenericDaoJpa<Post, Long> implements PostDao {
    public PostDaoJpa(EntityManager em) {
        super(em, Post.class);
    }
}
