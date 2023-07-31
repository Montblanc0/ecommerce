create table if not exists categorie
(
    ID_CATEGORIA int unsigned auto_increment
        primary key,
    Categoria    varchar(30) not null
)
    collate = utf8_unicode_ci;

create index if not exists ix_Categoria
    on categorie (Categoria);

