create table if not exists spedizioni
(
    ID_SPEDIZIONE    int unsigned auto_increment
        primary key,
    Spedizione       char(9)       not null,
    PrezzoSpedizione decimal(4, 2) not null
);

