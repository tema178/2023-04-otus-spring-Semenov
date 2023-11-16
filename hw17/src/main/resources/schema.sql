drop table IF EXISTS AUTHORS cascade;
create TABLE AUTHORS
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR(255)
);

drop table IF EXISTS GENRES cascade;
create TABLE GENRES
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR(255),

    CONSTRAINT GENRE_NAME_UNIQUE
    UNIQUE ( NAME )
);

drop table IF EXISTS BOOKS cascade;
create TABLE BOOKS
(
    ID   SERIAL PRIMARY KEY,
    NAME VARCHAR(255),
    AUTHOR_ID BIGINT NOT NULL,
    GENRE_ID BIGINT NOT NULL,

    foreign key (AUTHOR_ID) references AUTHORS(ID),
    foreign key (GENRE_ID) references GENRES(ID)

);

drop table IF EXISTS COMMENTS cascade;
create TABLE COMMENTS
(
    ID SERIAL PRIMARY KEY,
    BOOK_ID BIGINT references BOOKS(id) on delete cascade,
    BODY VARCHAR(2047)
);

drop table IF EXISTS users cascade;
create table users
(
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null
);

drop table IF EXISTS authorities cascade;
create table authorities
(
    id   SERIAL primary key,
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);