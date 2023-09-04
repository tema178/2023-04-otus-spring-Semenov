insert into authors (`name`)
values ('Ivan');

insert into authors (`name`)
values ('Nikolay');

insert into authors (`name`)
values ('Anna');

insert into genres (`name`)
values ('Action');
insert into genres (`name`)
values ('Adventure');
insert into genres (`name`)
values ('Comedy');
insert into genres (`name`)
values ('Crime');
insert into genres (`name`)
values ('Fantasy');
insert into genres (`name`)
values ('Horror');

insert into books (`name`, author_id, genre_id)
values ('Book of Ivan', 1, 1);
insert into books (`name`, author_id, genre_id)
values ('Book of Nikolay', 2, 1);

insert into comments (book_id, `body`)
values (2, 'Comment for book of Nikolay 1');

insert into comments (book_id, `body`)
values (2, 'Comment for book of Nikolay 2');