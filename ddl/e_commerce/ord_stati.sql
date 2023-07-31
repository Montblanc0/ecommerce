create table if not exists ord_stati
(
    ID_STATO    tinyint unsigned not null
        primary key,
    StatoOrdine varchar(30)      not null
)
    collate = utf8_unicode_ci;

