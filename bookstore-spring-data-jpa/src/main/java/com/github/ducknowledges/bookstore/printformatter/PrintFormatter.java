package com.github.ducknowledges.bookstore.printformatter;

import org.springframework.data.domain.Page;

public interface PrintFormatter<T> {
    String format(T type);

    String format(Page<T> page);
}
