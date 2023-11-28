drop table IF EXISTS AUTHORS;
create TABLE AUTHORS
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    MONGO_ID VARCHAR(255)
);

drop table IF EXISTS GENRES;
create TABLE GENRES
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    MONGO_ID VARCHAR(255),

    CONSTRAINT GENRE_NAME_UNIQUE
    UNIQUE ( NAME )
);

drop table IF EXISTS BOOKS;
create TABLE BOOKS
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    AUTHOR_ID BIGINT NOT NULL,
    GENRE_ID BIGINT NOT NULL,
    MONGO_ID VARCHAR(255),

    foreign key (AUTHOR_ID) references AUTHORS(ID),
    foreign key (GENRE_ID) references GENRES(ID)

);

drop table IF EXISTS COMMENTS;
create TABLE COMMENTS
(
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    BOOK_ID BIGINT references BOOKS(id) on delete cascade,
    BODY VARCHAR(2047)
);