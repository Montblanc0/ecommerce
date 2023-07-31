create table if not exists indirizzi
(
    ID_INDIRIZZO         int unsigned auto_increment
        primary key,
    indirizzo            varchar(60)  not null,
    indirizzo_secondario varchar(60)  null,
    cap                  char(5)      not null,
    citta                varchar(40)  not null,
    provincia            char(2)      not null,
    note                 varchar(300) null
);

