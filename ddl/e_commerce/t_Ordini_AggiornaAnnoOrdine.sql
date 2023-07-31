create definer = root@localhost trigger if not exists t_Ordini_AggiornaAnnoOrdine
    before insert
    on ordini
    for each row
    SET NEW.AnnoOrdine = YEAR(CURRENT_DATE);

