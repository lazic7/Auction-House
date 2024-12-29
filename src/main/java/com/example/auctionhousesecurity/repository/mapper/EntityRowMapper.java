package com.example.auctionhousesecurity.repository.mapper;

import com.example.auctionhousesecurity.model.Account;
import com.example.auctionhousesecurity.model.Authority;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;

public class EntityRowMapper {

    public static RowMapper<Account> accountRowMapper() {

        return (rs, rowNum) -> {
            Account account = new Account();

            account.setId(UUID.fromString(rs.getString(1)));
            account.setFirstName(rs.getString(2));
            account.setLastName(rs.getString(3));
            account.setUsername(rs.getString(4));
            account.setEmail(rs.getString(5));
            account.setPassword(rs.getString(6));
            account.setCreatedAt(rs.getTimestamp(7).toLocalDateTime());
            account.setClosedAt(rs.getTimestamp(8) != null ? rs.getTimestamp(8).toLocalDateTime() : null);
            account.setVerified(rs.getBoolean(9));
            account.setLocked(rs.getBoolean(10));
            account.setHashIdentifier(rs.getString(11));

            return account;
        };
    }

    public static RowMapper<Authority> authorityRowMapper() {
        return (rs, rowNum) -> {
            Authority authority = new Authority();

            authority.setId(UUID.fromString(rs.getString(1)));
            authority.setAuthorityName(rs.getString(2));
            authority.setAssignedAt(rs.getTimestamp(3).toLocalDateTime());
            authority.setRevokedAt(rs.getTimestamp(4) != null ? rs.getTimestamp(4).toLocalDateTime() : null);

            return authority;
        };
    }
}
