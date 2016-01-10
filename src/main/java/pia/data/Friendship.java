package pia.data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "friendship_pairs")
public class Friendship extends BaseEntity<Integer> {
    private int id;
    private Account first;
    private Account second;
    private Timestamp date;

    @Id
    @Column(name = "pair_id")
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "first", nullable = false)
    public Account getFirst() {
        return first;
    }

    public void setFirst(Account first) {
        this.first = first;
    }

    @OneToOne
    @JoinColumn(name = "second", nullable = false)
    public Account getSecond() {
        return second;
    }

    public void setSecond(Account second) {
        this.second = second;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friendship that = (Friendship) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                '}';
    }
}
