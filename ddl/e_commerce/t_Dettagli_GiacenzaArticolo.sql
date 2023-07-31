create definer = root@localhost trigger if not exists t_Dettagli_GiacenzaArticolo
    after insert
    on ordini_dettagli
    for each row
    call r_AggiornaGiacenzaArticolo(NEW.p_Articolo, NEW.p_Ordine);

