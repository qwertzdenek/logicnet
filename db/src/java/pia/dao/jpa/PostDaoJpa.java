package pia.dao.jpa;

import pia.dao.PostDao;
import pia.data.Post;

public class PostDaoJpa extends GenericDaoJpa<Post, Long> implements PostDao {
    public PostDaoJpa() {
        super(Post.class);
    }
}
