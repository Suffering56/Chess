--liquibase formatted sql

--changeset Magic:1
create table "user" (
	id bigserial not null
		constraint user_pkey
			primary key,
	login varchar(255) not null,
	password varchar(255) not null
);

create unique index user_login_uindex
	on "user" (login)
;

