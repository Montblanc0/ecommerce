create table if not exists ordini
(
    ID_ORDINE     int unsigned auto_increment
        primary key,
    NumeroOrdine  mediumint(6) unsigned                        not null,
    p_Cliente     int unsigned                                 not null,
    p_Indirizzo   int unsigned                                 not null,
    p_Spedizione  int unsigned                                 not null,
    p_StatoOrdine tinyint unsigned default 0                   not null,
    AnnoOrdine    year                                         not null,
    DataOrdine    datetime         default current_timestamp() not null,
    Sconto        decimal(6, 2)    default 0.00                not null,
    TotaleOrdine  decimal(7, 2)    default 0.00                not null,
    constraint ix_NumeroOrdine_Anno
        unique (NumeroOrdine, AnnoOrdine),
    constraint ordini_ibfk_2
        foreign key (p_StatoOrdine) references ord_stati (ID_STATO),
    constraint ordini_ibfk_3
        foreign key (p_Cliente) references utenti (ID_UTENTE)
            on update cascade,
    constraint ordini_ibfk_4
        foreign key (p_Spedizione) references spedizioni (ID_SPEDIZIONE)
            on update cascade,
    constraint ordini_ibfk_5
        foreign key (p_Indirizzo) references indirizzi (ID_INDIRIZZO)
            on update cascade
)
    collate = utf8_unicode_ci;

create index if not exists ix_Ordini_Cliente
    on ordini (p_Cliente);

create index if not exists ix_Ordini__p_Indirizzo
    on ordini (p_Indirizzo);

create index if not exists ix_Ordini__p_Spedizione
    on ordini (p_Spedizione);

create index if not exists ix_ordini_StatoOrdini
    on ordini (p_StatoOrdine);

