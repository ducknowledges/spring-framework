package com.github.ducknowledges.bookstore.printformatter;

import java.util.List;

public interface PrintFormatter<T> {
    String format(T type);

    String format(List<T> types);
}
