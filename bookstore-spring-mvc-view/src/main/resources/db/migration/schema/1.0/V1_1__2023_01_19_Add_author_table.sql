--date: 2023-01-19
--author: ducknowledges

create table author
(
    id   bigint auto_increment primary key,
    name varchar(255) not null unique
);