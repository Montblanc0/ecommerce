create table if not exists art_marche
(
    ID_MARCA int unsigned auto_increment
        primary key,
    Marca    varchar(30) not null,
    constraint ix_marca
        unique (Marca)
)
    collate = utf8_unicode_ci;

