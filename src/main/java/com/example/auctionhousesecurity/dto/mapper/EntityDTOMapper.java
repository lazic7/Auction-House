package com.example.auctionhousesecurity.dto.mapper;

import java.util.List;

public interface EntityDTOMapper<T, U> {

    U map(final T value);

    List<U> mapToList(final List<T> values);
}
