create definer = root@localhost trigger if not exists t_Dettagli_TotaleArticolo
    before insert
    on ordini_dettagli
    for each row
    SET NEW.TotaleArticolo = (SELECT a.PrezzoArticolo
                              FROM articoli a
                              WHERE a.ID_ARTICOLO = NEW.p_Articolo) * NEW.QuantitaArticolo;

