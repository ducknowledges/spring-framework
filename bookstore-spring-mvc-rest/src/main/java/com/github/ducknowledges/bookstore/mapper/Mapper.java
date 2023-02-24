package com.github.ducknowledges.bookstore.mapper;

public interface Mapper<T1, T2> extends PageResponseMapper<T1, T2> {

    T1 toDomain(T2 dto);

    T2 toDto(T1 domain);

}
