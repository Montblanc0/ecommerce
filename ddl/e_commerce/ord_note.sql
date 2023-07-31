create table if not exists ord_note
(
    p_Ordine   int unsigned not null
        primary key,
    NotaOrdine varchar(200) not null,
    constraint ord_note_ibfk_1
        foreign key (p_Ordine) references ordini (ID_ORDINE)
            on update cascade on delete cascade
)
    collate = utf8_unicode_ci;

