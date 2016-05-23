# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table card (
  card_id                       integer not null,
  date                          timestamp,
  from_user_id                  integer,
  to_user_id                    integer,
  title                         varchar(255),
  detail                        varchar(255),
  message                       varchar(255),
  category_id                   integer,
  good_count                    integer,
  constraint pk_card primary key (card_id)
);
create sequence card_seq;

create table category (
  category_id                   integer not null,
  category_cd                   varchar(255),
  category_name                 varchar(255),
  constraint pk_category primary key (category_id)
);
create sequence category_seq;

create table department (
  department_id                 integer not null,
  department_cd                 varchar(255),
  section_id                    integer,
  department_name               varchar(255),
  constraint pk_department primary key (department_id)
);
create sequence department_seq;

create table permission (
  permission_id                 integer not null,
  permission_name               varchar(255),
  constraint pk_permission primary key (permission_id)
);
create sequence permission_seq;

create table section (
  section_id                    integer not null,
  section_cd                    varchar(255),
  section_name                  varchar(255),
  constraint pk_section primary key (section_id)
);
create sequence section_seq;

create table user (
  user_id                       integer not null,
  user_cd                       varchar(255),
  user_pass                     varchar(255),
  department_id                 integer,
  user_name                     varchar(255),
  permission_id                 integer,
  constraint pk_user primary key (user_id)
);
create sequence user_seq;


# --- !Downs

drop table if exists card;
drop sequence if exists card_seq;

drop table if exists category;
drop sequence if exists category_seq;

drop table if exists department;
drop sequence if exists department_seq;

drop table if exists permission;
drop sequence if exists permission_seq;

drop table if exists section;
drop sequence if exists section_seq;

drop table if exists user;
drop sequence if exists user_seq;

