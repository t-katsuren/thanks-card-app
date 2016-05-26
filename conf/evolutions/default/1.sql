# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table card (
  id                            integer not null,
  date                          timestamp,
  from_user_id                  integer,
  to_user_id                    integer,
  title                         varchar(255),
  detail                        varchar(255),
  message                       varchar(255),
  category_id                   integer,
  good_count                    integer,
  constraint pk_card primary key (id)
);
create sequence card_seq;

create table category (
  id                            integer not null,
  category_cd                   varchar(255),
  category_name                 varchar(255),
  constraint pk_category primary key (id)
);
create sequence category_seq;

create table department (
  id                            integer not null,
  department_cd                 varchar(255),
  section_id                    integer,
  department_name               varchar(255),
  constraint pk_department primary key (id)
);
create sequence department_seq;

create table master (
  id                            integer not null,
  from_department_name          varchar(255),
  from_user_name                varchar(255),
  to_department_name            varchar(255),
  to_user_name                  varchar(255),
  category_name                 varchar(255),
  title                         varchar(255),
  date                          timestamp,
  good_count                    integer,
  constraint pk_master primary key (id)
);
create sequence master_seq;

create table permission (
  id                            integer not null,
  permission_name               varchar(255),
  constraint pk_permission primary key (id)
);
create sequence permission_seq;

create table section (
  id                            integer not null,
  section_cd                    varchar(255),
  section_name                  varchar(255),
  constraint pk_section primary key (id)
);
create sequence section_seq;

create table user (
  id                            integer not null,
  user_cd                       varchar(255),
  user_pass                     varchar(255),
  department_id                 integer,
  user_name                     varchar(255),
  permission_id                 integer,
  constraint pk_user primary key (id)
);
create sequence user_seq;

alter table card add constraint fk_card_from_user_id foreign key (from_user_id) references user (id) on delete restrict on update restrict;
create index ix_card_from_user_id on card (from_user_id);

alter table card add constraint fk_card_to_user_id foreign key (to_user_id) references user (id) on delete restrict on update restrict;
create index ix_card_to_user_id on card (to_user_id);

alter table card add constraint fk_card_category_id foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_card_category_id on card (category_id);

alter table department add constraint fk_department_section_id foreign key (section_id) references section (id) on delete restrict on update restrict;
create index ix_department_section_id on department (section_id);

alter table user add constraint fk_user_department_id foreign key (department_id) references department (id) on delete restrict on update restrict;
create index ix_user_department_id on user (department_id);

alter table user add constraint fk_user_permission_id foreign key (permission_id) references permission (id) on delete restrict on update restrict;
create index ix_user_permission_id on user (permission_id);


# --- !Downs

alter table card drop constraint if exists fk_card_from_user_id;
drop index if exists ix_card_from_user_id;

alter table card drop constraint if exists fk_card_to_user_id;
drop index if exists ix_card_to_user_id;

alter table card drop constraint if exists fk_card_category_id;
drop index if exists ix_card_category_id;

alter table department drop constraint if exists fk_department_section_id;
drop index if exists ix_department_section_id;

alter table user drop constraint if exists fk_user_department_id;
drop index if exists ix_user_department_id;

alter table user drop constraint if exists fk_user_permission_id;
drop index if exists ix_user_permission_id;

drop table if exists card;
drop sequence if exists card_seq;

drop table if exists category;
drop sequence if exists category_seq;

drop table if exists department;
drop sequence if exists department_seq;

drop table if exists master;
drop sequence if exists master_seq;

drop table if exists permission;
drop sequence if exists permission_seq;

drop table if exists section;
drop sequence if exists section_seq;

drop table if exists user;
drop sequence if exists user_seq;

