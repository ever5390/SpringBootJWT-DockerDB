package com.rpej.security.User;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Basic
    @NotBlank(message = "username field cannot be empty")
    private String username;

    @Basic
    @NotBlank(message = "email field cannot be empty")
    private String email;
    private String lastname;

    @NotBlank(message = "firstname field cannot be empty")
    private String firstname;

    private String country;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Integer id, String username, String email, String lastname, String firstname, String country, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.country = country;
        this.password = password;
        this.role = role;
    }

    public User() {}

    public User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.lastname = builder.lastname;
        this.firstname = builder.firstname;
        this.country = builder.country;
        this.password = builder.password;
        this.role = builder.role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
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

    public static class Builder {
        private  Integer id;
        private  String username;
        private  String lastname;
        private  String email;
        private  String firstname;
        private  String country;
        private  String password;
        private  Role role;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", country='" + country + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
