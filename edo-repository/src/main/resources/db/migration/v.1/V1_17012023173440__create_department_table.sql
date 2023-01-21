create table if not exists department
(
    id            bigserial not null primary key references address (id),
    short_name    text,               --Короткое имя, возможно аббревиатура
    full_name     text,               --Полное имя
    address_id    bigint    not null, --ключ адреса
    archived_date timestamp(6),       --дата закрытия
    creation_date timestamp(6),       --дата открытия
    external_id   bigint,             --ключ внешней таблицы
    phone         varchar(255),       --номер телефона
    department_id bigint              --ключ вышестоящего отдела
);

comment on column department.id is 'id';
comment on column department.short_name is 'аббревиатура';
comment on column department.full_name is 'Полное имя';
comment on column department.address_id is 'ключ адреса';
comment on column department.archived_date is 'дата закрытия';
comment on column department.creation_date is 'дата основания';
comment on column department.external_id is 'ключ внешней таблицы';
comment on column department.phone is 'номер телефона';
comment on column department.department_id is 'ключ вышестоящего отдела';


alter table if exists department
    add constraint fk_uq1_address_department foreign key (address_id) references address;

alter table if exists department
    add constraint jk_uq1_department_department foreign key (department_id) references department;


insert into address(full_address, street, house, index, housing, building, city, region, country, flat)
VALUES ('Moscow', 'Rogozin street', '10', '1234', ' ', ' ', 'Moscow', 'Moscow region', 'Russia', ' ');

INSERT INTO department (short_name, full_name, address_id, archived_date, creation_date, external_id, phone,
                        department_id)
VALUES ('StS', 'Stock sale', 1, '2023-01-18 14:55:16.000000', '2023-01-18 14:55:18.000000', 0, '89999999999',
        1);

