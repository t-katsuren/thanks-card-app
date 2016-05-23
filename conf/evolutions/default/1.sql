# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table permission (
  permission_id                 integer not null,
  permission_name               varchar(255),
  constraint pk_permission primary key (permission_id)
);
create sequence permission_seq;


# --- !Downs

drop table if exists permission;
drop sequence if exists permission_seq;

