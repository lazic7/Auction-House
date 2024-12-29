package com.example.auctionhousesecurity.model;

import jakarta.validation.constraints.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class Account implements UserDetails, CredentialsContainer {

    private UUID id;

    @Size(max=35, message = "First name input is too long. Max 35 characters.")
    @NotBlank(message = "First name field is required.")
    private String firstName;

    @Size(max=35, message = "Last name input is too long. Max 35 characters.")
    @NotBlank(message = "Last name field is required.")
    private String lastName;

    @Size(max=35, message = "Username input is too long. Max 35 characters.")
    @NotBlank(message = "Username field is required.")
    private String username;

    @Size(max = 320, message = "Email input is too long. Max 320 characters.")
    @NotBlank(message = "Email field is required.")
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email input is not valid.")
    private String email;

    @Size(max = 8, message = "Password input is too short. Enter at least 8 characters.")
    @NotBlank(message = "Password field is required.")
    /*@Pattern(
            regexp = "^[A-Za-z\\d@$!%*?&]$",
            message = "Password input is not valid."
    )
    @Pattern(regexp = "^(?=.*[a-z])$", message = "Password input has to have at least one uppercase letter.")
    @Pattern(regexp = "^(?=.*[A-Z])$", message = "Password input has to have at least one uppercase letter.")
    @Pattern(regexp = "^(?=.*\\d)$", message = "Password input has to have at least one digit.")
    @Pattern(regexp = "^(?=.*[@$!%*?&])$", message = "Password input has to have at least one special character.")*/
    private String password;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    //user verified email flag
    @NotNull
    private Boolean verified;

    //user locked/banned flag
    @NotNull
    private Boolean locked;

    @NotNull
    private String hashIdentifier;

    @NotEmpty
    private Set<Authority> authorities;

    public Account() {

    }

    public Account(
            UUID id,
            String firstName,
            String lastName,
            String username,
            String email,
            String password,
            LocalDateTime createdAt,
            LocalDateTime closedAt,
            Boolean verified,
            Boolean locked,
            String hashIdentifier,
            Set<Authority> authorities
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.verified = verified;
        this.locked = locked;
        this.hashIdentifier = hashIdentifier;
        this.authorities = authorities;
    }

    //this method is used by authentication manager
    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    //this method is used by authentication manager
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return verified && !locked;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getHashIdentifier() {
        return hashIdentifier;
    }

    public void setHashIdentifier(String hashIdentifier) {
        this.hashIdentifier = hashIdentifier;
    }

    public Set<Authority> getAuthoritiesSet() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
