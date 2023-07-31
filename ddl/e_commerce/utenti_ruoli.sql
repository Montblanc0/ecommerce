create table if not exists utenti_ruoli
(
    p_Utente int unsigned     not null,
    p_Ruolo  tinyint unsigned not null,
    constraint idx__p_Utente_p_Ruolo
        unique (p_Utente, p_Ruolo),
    constraint utenti_ruoli_ibfk_1
        foreign key (p_Utente) references utenti (ID_UTENTE)
            on update cascade on delete cascade,
    constraint utenti_ruoli_ibfk_2
        foreign key (p_Ruolo) references ruoli (ID_RUOLO)
            on update cascade on delete cascade
);

create index if not exists xd__p_Ruolo
    on utenti_ruoli (p_Ruolo);

create index if not exists xd__p_Utente
    on utenti_ruoli (p_Utente);

