DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255)
);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),

    CONSTRAINT GENRE_NAME_UNIQUE
    UNIQUE ( NAME )
);

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    AUTHOR_ID BIGINT NOT NULL,
    GENRE_ID BIGINT NOT NULL,

    foreign key (AUTHOR_ID) references AUTHORS(ID),
    foreign key (GENRE_ID) references GENRES(ID)

);

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS
(
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    BOOK_ID BIGINT references BOOKS(id) on delete cascade,
    BODY VARCHAR(2047)
);


create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);