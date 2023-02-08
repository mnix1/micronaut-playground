--liquibase formatted sql

--changeset dev:1
create table if not exists company
(
    id                uuid primary key,
    name              varchar,
    industry          varchar,
    type              varchar,
    employees         int,
    created_date_time timestamp,
    facilities        text
);