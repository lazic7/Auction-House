package com.example.auctionhousesecurity.dto.mapper.implementation;

import com.example.auctionhousesecurity.dto.mapper.EntityDTOMapper;
import com.example.auctionhousesecurity.dto.response.AccountResponseDTO;
import com.example.auctionhousesecurity.model.Account;
import com.example.auctionhousesecurity.model.Authority;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountToAccountDTOMapper implements EntityDTOMapper<Account, AccountResponseDTO> {
    @Override
    public AccountResponseDTO map(final @Valid Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getUsername(),
                account.getEmail(),
                account.getCreatedAt(),
                account.getAuthoritiesSet()
                        .stream()
                        .map(Authority::getAuthorityName)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public List<AccountResponseDTO> mapToList(final List<Account> accounts) {
        return accounts
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
