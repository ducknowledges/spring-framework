create table book
(
    id        bigint auto_increment primary key,
    name      varchar(255) not null,
    author_id bigint          not null,
    genre_id  bigint          not null
);

alter table book
    add foreign key (author_id) REFERENCES author (id);
alter table book
    add foreign key (genre_id) REFERENCES genre (id);