create table if not exists articoli
(
    ID_ARTICOLO         int unsigned auto_increment
        primary key,
    p_marca             int unsigned                        not null,
    ModelloArticolo     varchar(20)                         not null,
    NomeArticolo        varchar(110)                        not null,
    PrezzoArticolo      decimal(6, 2)                       not null,
    ScontoPerUnita      decimal(6, 2) unsigned default 0.00 not null,
    GiacenzaArticolo    smallint unsigned                   not null,
    GiacenzaMinArticolo tinyint unsigned                    not null,
    DescrizioneArticolo varchar(300)                        not null,
    constraint articoli_ibfk_1
        foreign key (p_marca) references art_marche (ID_MARCA)
            on update cascade on delete cascade
)
    collate = utf8_unicode_ci;

create index if not exists ix_articolo_marca
    on articoli (p_marca);

