--date: 2023-01-31
--author: ducknowledges

create table book_comment
(
    id      bigint auto_increment primary key,
    message varchar(255) not null,
    book_id bigint       not null
);

alter table book_comment
    add foreign key (book_id) REFERENCES book (id);