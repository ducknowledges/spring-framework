--date: 2023-01-20
--author: ducknowledges

create table genre
(
    id   bigint auto_increment primary key,
    name varchar(255) not null unique
);