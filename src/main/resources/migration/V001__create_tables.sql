CREATE TABLE user_entity (
    id uuid not null,
    avatar varchar(255),
    email varchar(255) not null,
    name varchar(255),
    password varchar(255) not null,
    role varchar(255),
    primary key (id));

CREATE TABLE news_entity (
    id int8 not null,
    description varchar(255),
    image varchar(255),
    title varchar(255),
    username varchar(255),
    user_id uuid,
    primary key (id));

CREATE TABLE tag_entity (
    id int8 not null,
    title varchar(255),
    news_id int8,
    primary key (id));

CREATE TABLE log_entity (
    id int8 not null,
    method varchar(255),
    status varchar(255),
    time timestamp,
    url varchar(255),
    primary key (id));

ALTER TABLE if EXISTS news_entity
    add constraint news_entity_user_fk
    foreign key (user_id) references user_entity;

ALTER TABLE if EXISTS tag_entity
    add constraint tag_entity_news_fk
    foreign key (news_id) references news_entity;