package com.example.auctionhousesecurity.dto.response;

import java.time.LocalDateTime;

public record ExceptionResponse(
        Object message,
        LocalDateTime timestamp
) {
}
