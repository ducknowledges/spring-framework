package com.github.ducknowledges.bookstore.mapper;

import com.github.ducknowledges.bookstore.dto.PageResponseDto;
import org.springframework.data.domain.Page;

public interface PageResponseMapper<T1, T2> {
    PageResponseDto<T2> getPageResponseDto(Page<T1> domainPage);

}
