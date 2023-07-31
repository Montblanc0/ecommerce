create table if not exists anagrafica
(
    ID_ANAGRAFICA  int unsigned auto_increment
        primary key,
    RagioneSociale varchar(40)  default '' null,
    Cognome        varchar(25)             null,
    Nome           varchar(25)             null,
    Email          varchar(100) default '' not null,
    Telefono       varchar(15)  default '' not null,
    Mobile         varchar(15)             null,
    Indirizzo      varchar(60)  default '' not null,
    CAP            char(5)      default '' not null,
    Citta          varchar(40)  default '' not null,
    Provincia      char(2)      default '' not null,
    Note           varchar(300)            null
)
    collate = utf8_unicode_ci;

create index if not exists ix_Cognome
    on anagrafica (Cognome);

create index if not exists ix_RagioneSociale
    on anagrafica (RagioneSociale);

create index if not exists ix_anagrafica_Email
    on anagrafica (Email);

