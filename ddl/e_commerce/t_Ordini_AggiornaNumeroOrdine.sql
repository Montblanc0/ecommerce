create definer = root@localhost trigger if not exists t_Ordini_AggiornaNumeroOrdine
    before insert
    on ordini
    for each row
BEGIN
    -- Declare a variable to hold the current year
    SET @currentYear = YEAR(CURRENT_DATE);

    -- Check if the current year already exists in reset_contatore_anno table
    SET @resetExists = (SELECT 1 FROM reset_contatore_anno WHERE ANNO = @currentYear LIMIT 1);

    -- If resetExists is not null, the counter has been reset for the current year, increment NumeroOrdine
    IF @resetExists IS NOT NULL THEN
        -- Get the lastNumeroOrdine for the current year using a user-defined variable
        SET @lastNumeroOrdine := (SELECT COALESCE(MAX(NumeroOrdine), 0) + 1
                                  FROM ordini
                                  WHERE YEAR(DataOrdine) = @currentYear);

        -- Set the incremented NumeroOrdine for the newly inserted row in the Ordine table
        SET NEW.NumeroOrdine = @lastNumeroOrdine;
    ELSE
        -- Otherwise, set NumeroOrdine to 1 for the newly inserted row in the Ordine table
        SET NEW.NumeroOrdine = 1;

        -- Insert the current year into the reset_contatore_anno table as a reset flag
        INSERT INTO reset_contatore_anno (ANNO) VALUES (@currentYear);
    END IF;
END;

