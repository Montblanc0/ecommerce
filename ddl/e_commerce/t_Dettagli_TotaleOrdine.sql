create definer = root@localhost trigger if not exists t_Dettagli_TotaleOrdine
    after insert
    on ordini_dettagli
    for each row
    call r_AggiornaTotOrdine(NEW.p_Ordine);

