package com.Zaffierce.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(
            name="following_users",
            joinColumns ={ @JoinColumn(name="follower_profile_id")},
            inverseJoinColumns = {@JoinColumn(name="following_profile_id")}
    )
    Set<ApplicationUser> usersIFollow;

    @ManyToMany(mappedBy = "usersIFollow")
    Set<ApplicationUser> usersThatFollowMe;

    protected String username;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String bio;

    public List<Post> getPosts() { return posts; }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    List<Post> posts;

    public Set<ApplicationUser> getUsersIFollow() {
        return usersIFollow;
    }

    public Set<ApplicationUser> getUsersThatFollowMe() {
        return usersThatFollowMe;
    }

    public ApplicationUser() {}

    public ApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getDateOfBirth() { return this.dateOfBirth; }
    public String getBio() { return this.bio; }
    public long getId() { return this.id; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void followThisUser(ApplicationUser originalPoster) {
        usersIFollow.add(originalPoster);
    }

}
