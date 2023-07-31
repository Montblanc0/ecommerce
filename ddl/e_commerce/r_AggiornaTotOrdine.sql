create
    definer = root@localhost procedure r_AggiornaTotOrdine(IN Ordine int unsigned) modifies sql data
UPDATE `ordini`
SET Sconto       = (SELECT @sconto := SUM((ScontoPerUnita) * QuantitaArticolo)
                    FROM ordini_dettagli
                             JOIN articoli ON p_Articolo = ID_ARTICOLO
                    WHERE p_Ordine = Ordine),
    TotaleOrdine = (SELECT SUM(TotaleArticolo) - @sconto + PrezzoSpedizione
                    FROM ordini_dettagli
                             JOIN spedizioni ON p_Spedizione = ID_SPEDIZIONE
                    WHERE p_Ordine = Ordine)
WHERE ID_Ordine = Ordine;

