# --- !Ups

create table countries (
   id    BIGSERIAL not null,
   name  varchar(255),
   constraint pk_countries primary key (id)
);

# --- !Downs

drop table if exists countries;