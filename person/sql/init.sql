create table address (id  bigserial not null, apartment varchar(255), building varchar(255), city varchar(255), country varchar(255), details varchar(255), street varchar(255), region_id int8, primary key (id));
create table address_person (person_id int8 not null, address_id int8 not null, primary key (person_id, address_id));
create table contact (id  bigserial not null, contact_type varchar(255), value varchar(255), person_id int8, primary key (id));
create table identity_document (id  bigserial not null, document_exp_date date, document_number varchar(255), document_series varchar(255), document_type varchar(255), person_id int8, primary key (id));
create table person (id  bigserial not null, birth_date date, first_name varchar(255), hide boolean not null, last_name varchar(255), middle_name varchar(255), primary key (id));
create table region (id  bigserial not null, name varchar(255), primary key (id));
alter table region add constraint UK_ixr2itih2n9q41fv3qx6mbkrp unique (name);
alter table address add constraint FK4ljdc68rnke4txup1jlf1il4l foreign key (region_id) references region;
alter table address_person add constraint FKqfl3r2wwg312jlb5f8rn4aie8 foreign key (address_id) references address;
alter table address_person add constraint FKjh6nx1nkmjb1y511m5qbck27i foreign key (person_id) references person;
alter table contact add constraint FKjbcdaayhsa4dhcuc5q0kkw8et foreign key (person_id) references person;
alter table identity_document add constraint FKhdsc4tosyiek7cvnts8vo5c4p foreign key (person_id) references person;

insert into person (first_name, last_name, birth_date, middle_name, hide)
values ('Иван', 'Иванов', '1997-04-22', 'Иванович', false);

insert into identity_document (document_type, document_series, document_number,document_exp_date, person_id)
values ('Паспорт', '1234', '567890', '2025-10-10', (select id from person where id = 1));