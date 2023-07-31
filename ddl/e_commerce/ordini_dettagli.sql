create table if not exists ordini_dettagli
(
    p_Ordine         int unsigned                        not null,
    p_Articolo       int unsigned                        not null,
    TotaleArticolo   decimal(7, 2) unsigned default 0.00 not null,
    QuantitaArticolo tinyint unsigned                    not null,
    primary key (p_Ordine, p_Articolo),
    constraint ordini_dettagli_ibfk_1
        foreign key (p_Ordine) references ordini (ID_ORDINE)
            on update cascade on delete cascade,
    constraint ordini_dettagli_ibfk_2
        foreign key (p_Articolo) references articoli (ID_ARTICOLO)
)
    collate = utf8_unicode_ci;

create index if not exists ix_ordini_dettagli_p_articolo
    on ordini_dettagli (p_Articolo);

create index if not exists ix_ordini_dettagli_p_ordine
    on ordini_dettagli (p_Ordine);

