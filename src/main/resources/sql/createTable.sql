create table Account (
    number varchar(26) primary key,
    holder varchar(255) not null,
    funds decimal(20,8) default 0 not null
)