create
    definer = root@localhost procedure r_AggiornaGiacenzaArticolo(IN Articolo int unsigned, IN Ordine int unsigned)
Update `articoli`
SET GiacenzaArticolo = (SELECT (GiacenzaArticolo - QuantitaArticolo)
                        from `articoli`
                                 LEFT JOIN `ordini_dettagli` on p_Articolo = ID_ARTICOLO
                                 LEFT JOIN `ordini` on p_Ordine = ID_ORDINE
                        WHERE ID_ARTICOLO = Articolo
                          AND ID_ORDINE = Ordine)
WHERE ID_ARTICOLO = Articolo;

