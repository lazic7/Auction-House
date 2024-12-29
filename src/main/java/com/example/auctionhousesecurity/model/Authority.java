package com.example.auctionhousesecurity.model;

import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.UUID;

public class Authority implements GrantedAuthority {

    private UUID id;

    private String authorityName;

    private LocalDateTime assignedAt;

    private LocalDateTime revokedAt;

    public Authority() {
    }

    public Authority(
            UUID id,
            String authorityName,
            LocalDateTime assignedAt,
            LocalDateTime revokedAt
    ) {
        this.id = id;
        this.authorityName = authorityName;
        this.assignedAt = assignedAt;
        this.revokedAt = revokedAt;
    }

    //this method is used by authentication manager
    @Override
    public String getAuthority() {
        return authorityName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
