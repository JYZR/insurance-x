# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table claim (
  id                        bigint not null,
  insurance_id              bigint not null,
  damage                    varchar(255) not null,
  constraint pk_claim primary key (id))
;

create table insurance (
  id                        bigint not null,
  customer_username         varchar(255) not null,
  level                     integer not null,
  reg_number                varchar(255),
  constraint ck_insurance_level check (level in (0,1,2)),
  constraint pk_insurance primary key (id))
;

create table task (
  id                        bigint not null,
  label                     varchar(255),
  owner_username            varchar(255) not null,
  constraint pk_task primary key (id))
;

create table user (
  _type                     integer(31) not null,
  username                  varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  phone                     varchar(255),
  constraint pk_user primary key (username))
;

create sequence claim_seq;

create sequence insurance_seq;

create sequence task_seq;

create sequence user_seq;

alter table claim add constraint fk_claim_insurance_1 foreign key (insurance_id) references insurance (id) on delete restrict on update restrict;
create index ix_claim_insurance_1 on claim (insurance_id);
alter table insurance add constraint fk_insurance_customer_2 foreign key (customer_username) references user (username) on delete restrict on update restrict;
create index ix_insurance_customer_2 on insurance (customer_username);
alter table task add constraint fk_task_owner_3 foreign key (owner_username) references user (username) on delete restrict on update restrict;
create index ix_task_owner_3 on task (owner_username);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists claim;

drop table if exists insurance;

drop table if exists task;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists claim_seq;

drop sequence if exists insurance_seq;

drop sequence if exists task_seq;

drop sequence if exists user_seq;

