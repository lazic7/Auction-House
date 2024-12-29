package com.example.auctionhousesecurity.dto.response;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record AccountResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String username,
        String email,
        LocalDateTime createdAt,
        Set<String> authorities
) {
}
