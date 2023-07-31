create table if not exists utenti
(
    ID_UTENTE     int unsigned auto_increment
        primary key,
    username      varchar(36)                           not null,
    password      char(60)                              not null,
    p_Anagrafica  int unsigned                          not null,
    DataCreazione timestamp default current_timestamp() not null,
    DataModifica  timestamp default current_timestamp() not null,
    constraint utente__p_Anagrafica
        unique (p_Anagrafica),
    constraint utente__username
        unique (username),
    constraint utenti_ibfk_1
        foreign key (p_Anagrafica) references anagrafica (ID_ANAGRAFICA)
            on update cascade on delete cascade
);

