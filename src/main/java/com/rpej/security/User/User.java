package com.rpej.security.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

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

    /*
    @Enumerated(EnumType.STRING)
    private Role role;
*/
    private String role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss", timezone = "America/New_York")
    private Date lastLoginDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss", timezone = "America/New_York")
    private Date lastLoginDateDisplay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss", timezone = "America/New_York")
    private Date joinDate;
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginDateDisplay() {
        return lastLoginDateDisplay;
    }

    public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
        this.lastLoginDateDisplay = lastLoginDateDisplay;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }

    public User() {}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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
        return this.isNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }


}
