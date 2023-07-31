-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Lug 31, 2023 alle 13:01
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e_commerce`
--
CREATE DATABASE IF NOT EXISTS `e_commerce` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `e_commerce`;

-- --------------------------------------------------------

--
-- Struttura della tabella `anagrafica`
--

DROP TABLE IF EXISTS `anagrafica`;
CREATE TABLE IF NOT EXISTS `anagrafica` (
  `ID_ANAGRAFICA` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `RagioneSociale` varchar(40) DEFAULT '',
  `Cognome` varchar(25) DEFAULT NULL,
  `Nome` varchar(25) DEFAULT NULL,
  `Email` varchar(100) NOT NULL DEFAULT '',
  `Telefono` varchar(15) NOT NULL DEFAULT '',
  `Mobile` varchar(15) DEFAULT NULL,
  `Indirizzo` varchar(60) NOT NULL DEFAULT '',
  `CAP` char(5) NOT NULL DEFAULT '',
  `Citta` varchar(40) NOT NULL DEFAULT '',
  `Provincia` char(2) NOT NULL DEFAULT '',
  `Note` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID_ANAGRAFICA`),
  KEY `ix_anagrafica_Email` (`Email`),
  KEY `ix_Cognome` (`Cognome`) USING BTREE,
  KEY `ix_RagioneSociale` (`RagioneSociale`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `articoli`
--

DROP TABLE IF EXISTS `articoli`;
CREATE TABLE IF NOT EXISTS `articoli` (
  `ID_ARTICOLO` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `p_marca` int(10) UNSIGNED NOT NULL,
  `ModelloArticolo` varchar(20) NOT NULL,
  `NomeArticolo` varchar(110) NOT NULL,
  `PrezzoArticolo` decimal(6,2) NOT NULL,
  `ScontoPerUnita` decimal(6,2) UNSIGNED NOT NULL DEFAULT 0.00,
  `GiacenzaArticolo` smallint(5) UNSIGNED NOT NULL,
  `GiacenzaMinArticolo` tinyint(3) UNSIGNED NOT NULL,
  `DescrizioneArticolo` varchar(300) NOT NULL,
  PRIMARY KEY (`ID_ARTICOLO`),
  KEY `ix_articolo_marca` (`p_marca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Procedure `articoli`
--

DROP PROCEDURE IF EXISTS `r_AggiornaGiacenzaArticolo`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `r_AggiornaGiacenzaArticolo`(IN `Articolo` INT UNSIGNED, IN `Ordine` INT UNSIGNED)
Update `articoli`
SET GiacenzaArticolo = (SELECT (GiacenzaArticolo - QuantitaArticolo) from `articoli` LEFT JOIN `ordini_dettagli` on p_Articolo = ID_ARTICOLO LEFT JOIN `ordini` on p_Ordine = ID_ORDINE WHERE ID_ARTICOLO = Articolo AND ID_ORDINE = Ordine)
WHERE ID_ARTICOLO = Articolo$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struttura della tabella `articoli_sottocategorie`
--

DROP TABLE IF EXISTS `articoli_sottocategorie`;
CREATE TABLE IF NOT EXISTS `articoli_sottocategorie` (
  `p_Articolo` int(10) UNSIGNED NOT NULL,
  `p_Sottocategoria` int(10) UNSIGNED NOT NULL,
  UNIQUE KEY `ix_univocita_articoli_sottocategorie` (`p_Articolo`,`p_Sottocategoria`) USING BTREE,
  KEY `p_Sottocategoria` (`p_Sottocategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `art_marche`
--

DROP TABLE IF EXISTS `art_marche`;
CREATE TABLE IF NOT EXISTS `art_marche` (
  `ID_MARCA` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Marca` varchar(30) NOT NULL,
  PRIMARY KEY (`ID_MARCA`),
  UNIQUE KEY `ix_marca` (`Marca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `categorie`
--

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie` (
  `ID_CATEGORIA` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Categoria` varchar(30) NOT NULL,
  PRIMARY KEY (`ID_CATEGORIA`),
  KEY `ix_Categoria` (`Categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `indirizzi`
--

DROP TABLE IF EXISTS `indirizzi`;
CREATE TABLE IF NOT EXISTS `indirizzi` (
  `ID_INDIRIZZO` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `indirizzo` varchar(60) NOT NULL,
  `indirizzo_secondario` varchar(60) DEFAULT NULL,
  `cap` char(5) NOT NULL,
  `citta` varchar(40) NOT NULL,
  `provincia` char(2) NOT NULL,
  `note` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID_INDIRIZZO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `ordini`
--

DROP TABLE IF EXISTS `ordini`;
CREATE TABLE IF NOT EXISTS `ordini` (
  `ID_ORDINE` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NumeroOrdine` mediumint(6) UNSIGNED NOT NULL,
  `p_Cliente` int(10) UNSIGNED NOT NULL,
  `p_Indirizzo` int(10) UNSIGNED NOT NULL,
  `p_Spedizione` int(10) UNSIGNED NOT NULL,
  `p_StatoOrdine` tinyint(3) UNSIGNED NOT NULL DEFAULT 0,
  `AnnoOrdine` year(4) NOT NULL,
  `DataOrdine` datetime NOT NULL DEFAULT current_timestamp(),
  `Sconto` decimal(6,2) NOT NULL DEFAULT 0.00,
  `TotaleOrdine` decimal(7,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`ID_ORDINE`),
  UNIQUE KEY `ix_NumeroOrdine_Anno` (`NumeroOrdine`,`AnnoOrdine`) USING BTREE,
  KEY `ix_Ordini_Cliente` (`p_Cliente`),
  KEY `ix_ordini_StatoOrdini` (`p_StatoOrdine`),
  KEY `ix_Ordini__p_Indirizzo` (`p_Indirizzo`),
  KEY `ix_Ordini__p_Spedizione` (`p_Spedizione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Procedure `ordini`
--

DROP PROCEDURE IF EXISTS `AnnullaOrdine`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AnnullaOrdine`(IN `Ordine` INT UNSIGNED)
    MODIFIES SQL DATA
Update `ordini`
Set p_StatoOrdine = 5
Where ID_ORDINE = Ordine$$
DELIMITER ;
DROP PROCEDURE IF EXISTS `r_AggiornaTotOrdine`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `r_AggiornaTotOrdine`(IN `Ordine` INT UNSIGNED)
    MODIFIES SQL DATA
UPDATE `ordini`
    SET Sconto = (SELECT @sconto := SUM((ScontoPerUnita)*QuantitaArticolo) FROM ordini_dettagli JOIN articoli ON p_Articolo = ID_ARTICOLO WHERE p_Ordine = Ordine),
    TotaleOrdine = (SELECT SUM(TotaleArticolo) - @sconto + PrezzoSpedizione FROM ordini_dettagli JOIN spedizioni ON p_Spedizione = ID_SPEDIZIONE WHERE p_Ordine = Ordine)
    WHERE ID_Ordine = Ordine$$
DELIMITER ;

--
-- Trigger `ordini`
--

DROP TRIGGER IF EXISTS `t_Ordini_AggiornaAnnoOrdine`;
DELIMITER $$
CREATE TRIGGER `t_Ordini_AggiornaAnnoOrdine` BEFORE INSERT ON `ordini` FOR EACH ROW SET NEW.AnnoOrdine = YEAR(CURRENT_DATE)
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `t_Ordini_AggiornaNumeroOrdine`;
DELIMITER $$
CREATE TRIGGER `t_Ordini_AggiornaNumeroOrdine` BEFORE INSERT ON `ordini` FOR EACH ROW BEGIN
  -- Declare a variable to hold the current year
  SET @currentYear = YEAR(CURRENT_DATE);

  -- Check if the current year already exists in reset_contatore_anno table
  SET @resetExists = (SELECT 1 FROM reset_contatore_anno WHERE ANNO = @currentYear LIMIT 1);

  -- If resetExists is not null, the counter has been reset for the current year, increment NumeroOrdine
  IF @resetExists IS NOT NULL THEN
    -- Get the lastNumeroOrdine for the current year using a user-defined variable
    SET @lastNumeroOrdine := (
      SELECT COALESCE(MAX(NumeroOrdine), 0) + 1
      FROM ordini
      WHERE YEAR(DataOrdine) = @currentYear
    );
    
    -- Set the incremented NumeroOrdine for the newly inserted row in the Ordine table
    SET NEW.NumeroOrdine = @lastNumeroOrdine;
  ELSE
    -- Otherwise, set NumeroOrdine to 1 for the newly inserted row in the Ordine table
    SET NEW.NumeroOrdine = 1;
    
    -- Insert the current year into the reset_contatore_anno table as a reset flag
    INSERT INTO reset_contatore_anno (ANNO) VALUES (@currentYear);
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struttura della tabella `ordini_dettagli`
--

DROP TABLE IF EXISTS `ordini_dettagli`;
CREATE TABLE IF NOT EXISTS `ordini_dettagli` (
  `p_Ordine` int(10) UNSIGNED NOT NULL,
  `p_Articolo` int(10) UNSIGNED NOT NULL,
  `TotaleArticolo` decimal(7,2) UNSIGNED NOT NULL DEFAULT 0.00,
  `QuantitaArticolo` tinyint(3) UNSIGNED NOT NULL,
  PRIMARY KEY (`p_Ordine`,`p_Articolo`),
  KEY `ix_ordini_dettagli_p_ordine` (`p_Ordine`),
  KEY `ix_ordini_dettagli_p_articolo` (`p_Articolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Trigger `ordini_dettagli`
--

DROP TRIGGER IF EXISTS `t_Dettagli_GiacenzaArticolo`;
DELIMITER $$
CREATE TRIGGER `t_Dettagli_GiacenzaArticolo` AFTER INSERT ON `ordini_dettagli` FOR EACH ROW call r_AggiornaGiacenzaArticolo(NEW.p_Articolo, NEW.p_Ordine)
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `t_Dettagli_TotaleArticolo`;
DELIMITER $$
CREATE TRIGGER `t_Dettagli_TotaleArticolo` BEFORE INSERT ON `ordini_dettagli` FOR EACH ROW SET NEW.TotaleArticolo = (
        SELECT a.PrezzoArticolo
        FROM articoli a
        WHERE a.ID_ARTICOLO = NEW.p_Articolo
    ) * NEW.QuantitaArticolo
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `t_Dettagli_TotaleOrdine`;
DELIMITER $$
CREATE TRIGGER `t_Dettagli_TotaleOrdine` AFTER INSERT ON `ordini_dettagli` FOR EACH ROW call r_AggiornaTotOrdine(NEW.p_Ordine)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struttura della tabella `ord_note`
--

DROP TABLE IF EXISTS `ord_note`;
CREATE TABLE IF NOT EXISTS `ord_note` (
  `p_Ordine` int(10) UNSIGNED NOT NULL,
  `NotaOrdine` varchar(200) NOT NULL,
  PRIMARY KEY (`p_Ordine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `ord_stati`
--

DROP TABLE IF EXISTS `ord_stati`;
CREATE TABLE IF NOT EXISTS `ord_stati` (
  `ID_STATO` tinyint(3) UNSIGNED NOT NULL,
  `StatoOrdine` varchar(30) NOT NULL,
  PRIMARY KEY (`ID_STATO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `reset_contatore_anno`
--

DROP TABLE IF EXISTS `reset_contatore_anno`;
CREATE TABLE IF NOT EXISTS `reset_contatore_anno` (
  `ANNO` smallint(4) UNSIGNED NOT NULL,
  PRIMARY KEY (`ANNO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `ruoli`
--

DROP TABLE IF EXISTS `ruoli`;
CREATE TABLE IF NOT EXISTS `ruoli` (
  `ID_RUOLO` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ruolo` tinytext NOT NULL,
  PRIMARY KEY (`ID_RUOLO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `sottocategorie`
--

DROP TABLE IF EXISTS `sottocategorie`;
CREATE TABLE IF NOT EXISTS `sottocategorie` (
  `ID_SOTTOCATEGORIA` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `p_Categoria` int(10) UNSIGNED NOT NULL,
  `Sottocategoria` varchar(30) NOT NULL,
  PRIMARY KEY (`ID_SOTTOCATEGORIA`),
  UNIQUE KEY `ix_Sottocategoria` (`Sottocategoria`,`p_Categoria`) USING BTREE,
  KEY `ix_p_categoria` (`p_Categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `spedizioni`
--

DROP TABLE IF EXISTS `spedizioni`;
CREATE TABLE IF NOT EXISTS `spedizioni` (
  `ID_SPEDIZIONE` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Spedizione` char(9) NOT NULL,
  `PrezzoSpedizione` decimal(4,2) NOT NULL,
  PRIMARY KEY (`ID_SPEDIZIONE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

DROP TABLE IF EXISTS `utenti`;
CREATE TABLE IF NOT EXISTS `utenti` (
  `ID_UTENTE` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(36) NOT NULL,
  `password` char(60) NOT NULL,
  `p_Anagrafica` int(10) UNSIGNED NOT NULL,
  `DataCreazione` timestamp NOT NULL DEFAULT current_timestamp(),
  `DataModifica` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`ID_UTENTE`),
  UNIQUE KEY `utente__username` (`username`),
  UNIQUE KEY `utente__p_Anagrafica` (`p_Anagrafica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti_indirizzi`
--

DROP TABLE IF EXISTS `utenti_indirizzi`;
CREATE TABLE IF NOT EXISTS `utenti_indirizzi` (
  `p_Utente` int(10) UNSIGNED NOT NULL,
  `p_Indirizzo` int(10) UNSIGNED NOT NULL,
  UNIQUE KEY `ix_Utenti_Indirizzi__p_Utente__p__Indirizzo` (`p_Utente`,`p_Indirizzo`),
  KEY `ix_Utenti_Indirizzi__p_Utente` (`p_Utente`) USING BTREE,
  KEY `ix_Utenti_Indirizzi__p_Indirizzo` (`p_Indirizzo`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti_ruoli`
--

DROP TABLE IF EXISTS `utenti_ruoli`;
CREATE TABLE IF NOT EXISTS `utenti_ruoli` (
  `p_Utente` int(10) UNSIGNED NOT NULL,
  `p_Ruolo` tinyint(3) UNSIGNED NOT NULL,
  UNIQUE KEY `idx__p_Utente_p_Ruolo` (`p_Utente`,`p_Ruolo`) USING BTREE,
  KEY `xd__p_Ruolo` (`p_Ruolo`),
  KEY `xd__p_Utente` (`p_Utente`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `articoli`
--
ALTER TABLE `articoli`
  ADD CONSTRAINT `articoli_ibfk_1` FOREIGN KEY (`p_marca`) REFERENCES `art_marche` (`ID_MARCA`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `articoli_sottocategorie`
--
ALTER TABLE `articoli_sottocategorie`
  ADD CONSTRAINT `articoli_sottocategorie_ibfk_2` FOREIGN KEY (`p_Articolo`) REFERENCES `articoli` (`ID_ARTICOLO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `articoli_sottocategorie_ibfk_3` FOREIGN KEY (`p_Sottocategoria`) REFERENCES `sottocategorie` (`ID_SOTTOCATEGORIA`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `ordini`
--
ALTER TABLE `ordini`
  ADD CONSTRAINT `ordini_ibfk_2` FOREIGN KEY (`p_StatoOrdine`) REFERENCES `ord_stati` (`ID_STATO`),
  ADD CONSTRAINT `ordini_ibfk_3` FOREIGN KEY (`p_Cliente`) REFERENCES `utenti` (`ID_UTENTE`) ON UPDATE CASCADE,
  ADD CONSTRAINT `ordini_ibfk_4` FOREIGN KEY (`p_Spedizione`) REFERENCES `spedizioni` (`ID_SPEDIZIONE`) ON UPDATE CASCADE,
  ADD CONSTRAINT `ordini_ibfk_5` FOREIGN KEY (`p_Indirizzo`) REFERENCES `indirizzi` (`ID_INDIRIZZO`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `ordini_dettagli`
--
ALTER TABLE `ordini_dettagli`
  ADD CONSTRAINT `ordini_dettagli_ibfk_1` FOREIGN KEY (`p_Ordine`) REFERENCES `ordini` (`ID_ORDINE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ordini_dettagli_ibfk_2` FOREIGN KEY (`p_Articolo`) REFERENCES `articoli` (`ID_ARTICOLO`);

--
-- Limiti per la tabella `ord_note`
--
ALTER TABLE `ord_note`
  ADD CONSTRAINT `ord_note_ibfk_1` FOREIGN KEY (`p_Ordine`) REFERENCES `ordini` (`ID_ORDINE`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `sottocategorie`
--
ALTER TABLE `sottocategorie`
  ADD CONSTRAINT `sottocategorie_ibfk_1` FOREIGN KEY (`p_Categoria`) REFERENCES `categorie` (`ID_CATEGORIA`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `utenti`
--
ALTER TABLE `utenti`
  ADD CONSTRAINT `utenti_ibfk_1` FOREIGN KEY (`p_Anagrafica`) REFERENCES `anagrafica` (`ID_ANAGRAFICA`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `utenti_indirizzi`
--
ALTER TABLE `utenti_indirizzi`
  ADD CONSTRAINT `utenti_indirizzi_ibfk_1` FOREIGN KEY (`p_Utente`) REFERENCES `utenti` (`ID_UTENTE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utenti_indirizzi_ibfk_2` FOREIGN KEY (`p_Indirizzo`) REFERENCES `indirizzi` (`ID_INDIRIZZO`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `utenti_ruoli`
--
ALTER TABLE `utenti_ruoli`
  ADD CONSTRAINT `utenti_ruoli_ibfk_1` FOREIGN KEY (`p_Utente`) REFERENCES `utenti` (`ID_UTENTE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utenti_ruoli_ibfk_2` FOREIGN KEY (`p_Ruolo`) REFERENCES `ruoli` (`ID_RUOLO`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
