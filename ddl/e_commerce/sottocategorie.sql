create table if not exists sottocategorie
(
    ID_SOTTOCATEGORIA int unsigned auto_increment
        primary key,
    p_Categoria       int unsigned not null,
    Sottocategoria    varchar(30)  not null,
    constraint ix_Sottocategoria
        unique (Sottocategoria, p_Categoria),
    constraint sottocategorie_ibfk_1
        foreign key (p_Categoria) references categorie (ID_CATEGORIA)
            on update cascade on delete cascade
)
    collate = utf8_unicode_ci;

create index if not exists ix_p_categoria
    on sottocategorie (p_Categoria);

