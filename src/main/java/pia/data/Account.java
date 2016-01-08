package pia.data;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity<String> {
    private String name;
    private String password;
    private Date birthday;
    private String profilePicture;

    private Set<String> roles = new LinkedHashSet<>();
    private Set<Post> posts = new LinkedHashSet<>();
    private Set<Post> likedPosts = new LinkedHashSet<>();
    private Set<Account> friends = new LinkedHashSet<>();
    private Set<Account> friendRequests = new LinkedHashSet<>();

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

    @Temporal(value = TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(nullable = false)
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

    public void addRole(String role) {
        this.roles.add(role);
    }

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
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

    public void addLike(Post post) {
        this.likedPosts.add(post);
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends_list",
            joinColumns = @JoinColumn(name = "account_one", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "account_two", referencedColumnName = "account_id"))
    public Set<Account> getFriends() {
        return friends;
    }

    public void setFriends(Set<Account> friends) {
        this.friends = friends;
    }

    public void addFriend(Account a) {
        this.friends.add(a);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "friend_requests",
            joinColumns = @JoinColumn(name = "account_one", referencedColumnName = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "account_two", referencedColumnName = "account_id"))
    public Set<Account> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(Set<Account> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public void addFriendRequest(Account a) {
        this.friendRequests.add(a);
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
