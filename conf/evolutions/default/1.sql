# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table project (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_project primary key (id))
;

create table task (
  id                        bigint not null,
  label                     varchar(255),
  owner_username            varchar(255) not null,
  constraint pk_task primary key (id))
;

create table user (
  username                  varchar(255) not null,
  password                  varchar(255),
  constraint pk_user primary key (username))
;


create table project_user (
  project_id                     bigint not null,
  user_username                  varchar(255) not null,
  constraint pk_project_user primary key (project_id, user_username))
;
create sequence project_seq;

create sequence task_seq;

create sequence user_seq;

alter table task add constraint fk_task_owner_1 foreign key (owner_username) references user (username) on delete restrict on update restrict;
create index ix_task_owner_1 on task (owner_username);



alter table project_user add constraint fk_project_user_project_01 foreign key (project_id) references project (id) on delete restrict on update restrict;

alter table project_user add constraint fk_project_user_user_02 foreign key (user_username) references user (username) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists project;

drop table if exists project_user;

drop table if exists task;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists project_seq;

drop sequence if exists task_seq;

drop sequence if exists user_seq;

