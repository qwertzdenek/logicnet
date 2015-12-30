package pia.data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity<Long> {
    private String content;
    private Date date;

    @Id
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Column(nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!content.equals(post.content)) return false;
        return date.equals(post.date);

    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return content;
    }
}
