package com.example.auctionhousesecurity.repository.implementation;

import com.example.auctionhousesecurity.model.Account;
import com.example.auctionhousesecurity.model.Authority;
import com.example.auctionhousesecurity.repository.AccountRepository;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.auctionhousesecurity.repository.mapper.EntityRowMapper.accountRowMapper;
import static com.example.auctionhousesecurity.repository.mapper.EntityRowMapper.authorityRowMapper;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;

    private final DSLContext create;

    @Autowired
    public AccountRepositoryImpl(
            JdbcTemplate jdbcTemplate,
            DSLContext dslContext
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.create = dslContext;
    }

    @Override
    public List<Account> findAll() {

        final String findAllActiveAccounts =
                create.select(
                            field("accounts.id"),
                            field("accounts.first_name"),
                            field("accounts.last_name"),
                            field("accounts.username"),
                            field("accounts.email"),
                            field("accounts.password"),
                            field("accounts.created_at"),
                            field("accounts.closed_at"),
                            field("accounts.verified"),
                            field("accounts.locked"),
                            field("accounts.hash_identifier")
                        )
                        .from(table("accounts"))
                        .where(field("accounts.closed_at").isNull())
                        .getSQL();

        final String findAllActiveAuthoritiesByAccountId =
                create.select(
                            field("authorities.id"),
                            field("authorities.authority_name"),
                            field("accounts_authorities.assigned_at"),
                            field("accounts_authorities.revoked_at")
                        )
                        .from(
                            table("accounts_authorities")
                            .join(table("authorities"))
                            .on(
                                field("accounts_authorities.authority_id").eq(field("authorities.id"))
                            )
                        )
                        .where(field("accounts_authorities.revoked_at").isNull())
                        .and(field("accounts_authorities.account_id").eq("?"))
                        .getSQL();

        try{

            List<Account> accounts = new ArrayList<>(
                    jdbcTemplate.query(
                            findAllActiveAccounts,
                            new Object[]{},
                            new int[]{},
                            accountRowMapper()
                    )
            );

            for (Account account : accounts) {

                List<Authority> authorities = new ArrayList<>(
                        jdbcTemplate.query(
                                findAllActiveAuthoritiesByAccountId,
                                new Object[]{account.getId()},
                                new int[]{Types.VARCHAR},
                                authorityRowMapper()
                        )
                );

                account.setAuthorities(new HashSet<>(authorities));
            }

            if (accounts.isEmpty()) {
                throw new EmptyResultDataAccessException("No accounts found.", 1);
            } else {
                return accounts;
            }

        } catch (EmptyResultDataAccessException exception) {
            log.info("Account service tried to load list of all accounts: {}", exception.getMessage());
            throw new EmptyResultDataAccessException(exception.getMessage(), 1);
        } catch (DataAccessException exception) {
            log.error("Account service tried to load list of all accounts: {}",exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Account findByEmail(final String email) {

        final String findAccountByEmail =
                create.select(
                            field("accounts.id"),
                            field("accounts.first_name"),
                            field("accounts.last_name"),
                            field("accounts.username"),
                            field("accounts.email"),
                            field("accounts.password"),
                            field("accounts.created_at"),
                            field("accounts.closed_at"),
                            field("accounts.verified"),
                            field("accounts.locked"),
                            field("accounts.hash_identifier")
                        )
                        .from(table("accounts"))
                        .where(field("accounts.email").eq("?"))
                        .getSQL();

        final String findAllActiveAuthoritiesByAccountId =
                create.select(
                            field("authorities.id"),
                            field("authorities.authority_name"),
                            field("accounts_authorities.assigned_at"),
                            field("accounts_authorities.revoked_at")
                        )
                        .from(
                            table("accounts_authorities")
                                .join(table("authorities"))
                                .on(
                                        field("accounts_authorities.authority_id").eq(field("authorities.id"))
                                )
                        )
                        .where(field("accounts_authorities.revoked_at").isNull())
                        .and(field("accounts_authorities.account_id").eq("?"))
                        .getSQL();

        try {
            Account account = jdbcTemplate.queryForObject(
                    findAccountByEmail,
                    new Object[] {email},
                    new int[] {Types.VARCHAR},
                    accountRowMapper()
            );

            assert account != null;

            List<Authority> authorities = new ArrayList<>(jdbcTemplate.query(
                    findAllActiveAuthoritiesByAccountId,
                    new Object[]{account.getId()},
                    new int[]{Types.VARCHAR},
                    authorityRowMapper()
            ));

            account.setAuthorities(new HashSet<>(authorities));

            return account;

        } catch (EmptyResultDataAccessException exception) {
            log.info("Account service tried to fetch account by email -> {}: {}", email, exception.getMessage());
            throw new EmptyResultDataAccessException(MessageFormat.format("Account with email {0} not found.", email), 1);
        } catch (DataAccessException exception) {
            log.error("Account service tried to fetch account by email -> {}: {}",email, exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }
}
