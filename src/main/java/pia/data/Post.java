package pia.data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity<Long> {
    private String content;
    private Account writer;
    private Timestamp dateTime;
    private Set<Account> likes;
    private int likesCount;

    @Id
    @GeneratedValue
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name="writer")
    public Account getWriter() {
        return writer;
    }

    public void setWriter(Account writtenBy) {
        this.writer = writtenBy;
    }

    @Column(name="date_time", nullable = false)
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp date) {
        this.dateTime = date;
    }

    @ManyToMany(mappedBy = "likedPosts")
    public Set<Account> getLikes() {
        return likes;
    }

    public void setLikes(Set<Account> likes) {
        this.likes = likes;
    }

    @Column(name="likes_count", nullable = false)
    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return content.equals(post.content) && dateTime.equals(post.dateTime);
    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + dateTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return content;
    }
}
