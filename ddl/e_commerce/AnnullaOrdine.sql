create
    definer = root@localhost procedure AnnullaOrdine(IN Ordine int unsigned) modifies sql data
Update `ordini`
Set p_StatoOrdine = 5
Where ID_ORDINE = Ordine;

