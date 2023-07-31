create table if not exists utenti_indirizzi
(
    p_Utente    int unsigned not null,
    p_Indirizzo int unsigned not null,
    constraint ix_Utenti_Indirizzi__p_Utente__p__Indirizzo
        unique (p_Utente, p_Indirizzo),
    constraint utenti_indirizzi_ibfk_1
        foreign key (p_Utente) references utenti (ID_UTENTE)
            on update cascade on delete cascade,
    constraint utenti_indirizzi_ibfk_2
        foreign key (p_Indirizzo) references indirizzi (ID_INDIRIZZO)
            on update cascade on delete cascade
);

create index if not exists ix_Utenti_Indirizzi__p_Indirizzo
    on utenti_indirizzi (p_Indirizzo);

create index if not exists ix_Utenti_Indirizzi__p_Utente
    on utenti_indirizzi (p_Utente);

