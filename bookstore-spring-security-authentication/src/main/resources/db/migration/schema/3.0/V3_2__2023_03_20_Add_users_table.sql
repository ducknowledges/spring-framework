create table users
(
    id                      bigint auto_increment primary key,
    username                varchar(50)  not null unique,
    password                varchar(100) not null,
    account_non_expired     boolean      not null,
    account_non_locked      boolean      not null,
    credentials_non_expired boolean      not null,
    enabled                 boolean      not null
);
