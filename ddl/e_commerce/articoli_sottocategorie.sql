create table if not exists articoli_sottocategorie
(
    p_Articolo       int unsigned not null,
    p_Sottocategoria int unsigned not null,
    constraint ix_univocita_articoli_sottocategorie
        unique (p_Articolo, p_Sottocategoria),
    constraint articoli_sottocategorie_ibfk_2
        foreign key (p_Articolo) references articoli (ID_ARTICOLO),
    constraint articoli_sottocategorie_ibfk_3
        foreign key (p_Sottocategoria) references sottocategorie (ID_SOTTOCATEGORIA)
            on update cascade on delete cascade
)
    collate = utf8_unicode_ci;

create index if not exists p_Sottocategoria
    on articoli_sottocategorie (p_Sottocategoria);

