package pia.data;

import javax.persistence.*;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity<String> {
    private String name;
    private String password;
    private Date birthday;
    private String profilePicture;

    private Set<String> roles;

    private Set<Post> posts;
    private Set<Post> likedPosts;
    private Set<Post> postHides;

    private Set<Account> friendRequests;
    private Set<Account> incomingFriendRequests;

    @Id
    @Column(name = "account_id")
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(name = "profile_picture", nullable = false)
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @ElementCollection
    @CollectionTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "role")
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    // Posts
    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_likes",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_hides",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
    public Set<Post> getPostHides() {
        return postHides;
    }

    public void setPostHides(Set<Post> postHides) {
        this.postHides = postHides;
    }

    // friend requests
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friend_requests",
            joinColumns = @JoinColumn(name = "account", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "target", referencedColumnName = "account_id"))
    public Set<Account> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(Set<Account> friendRequests) {
        this.friendRequests = friendRequests;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "friendRequests")
    public Set<Account> getIncomingFriendRequests() {
        return incomingFriendRequests;
    }

    public void setIncomingFriendRequests(Set<Account> incomingFriendRequests) {
        this.incomingFriendRequests = incomingFriendRequests;
    }

    @Override
    public String toString() {
        return "Account{" +
                "nickname='" + getId() + '\'' +
                ", birthday=" + birthday +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!getId().equals(account.getId())) return false;
        if (!password.equals(account.password)) return false;
        if (birthday != null ? !birthday.equals(account.birthday) : account.birthday != null) return false;
        return profilePicture.equals(account.profilePicture);

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + profilePicture.hashCode();
        return result;
    }
}
