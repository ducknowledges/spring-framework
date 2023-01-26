create table book
(
    id        int auto_increment primary key,
    name      varchar(255) not null,
    author_id int          not null,
    genre_id  int          not null
);

alter table book
    add foreign key (author_id) REFERENCES author (id);
alter table book
    add foreign key (genre_id) REFERENCES genre (id);