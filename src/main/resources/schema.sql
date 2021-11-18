-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema Inzynierka
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Inzynierka
-- -----------------------------------------------------

USE Inzynierka ;

-- -----------------------------------------------------
-- Table Inzynierka.`Uzytkownik`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`Uzytkownik` (
  idUzytkownik INT NOT NULL AUTO_INCREMENT,
  Login VARCHAR(25) NOT NULL,
  Saldo DECIMAL(6,2) NOT NULL,
  Aktywnosc TINYINT(1) NOT NULL,
  PrywatnoscKonta TINYINT(1) NOT NULL,
  CzyZbanowany TINYINT(1) NOT NULL,
  Rola TINYINT(1) NOT NULL,
  PRIMARY KEY (idUzytkownik),
  UNIQUE INDEX Login_UNIQUE (Login ASC)  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`ZadanieWyplaty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`ZadanieWyplaty` (
  idZadanieWyplaty INT NOT NULL AUTO_INCREMENT,
  KwotaWyplaty DECIMAL(6,2) NOT NULL,
  DataZadania DATETIME NOT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  PRIMARY KEY (idZadanieWyplaty),
  INDEX fk_ZadanieWyplaty_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  CONSTRAINT fk_ZadanieWyplaty_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`ObserwowanyUzytkownik`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`ObserwowanyUzytkownik` (
  Uzytkownik_idUzytkownik INT NOT NULL,
  Uzytkownik_idUzytkownikObserwowany INT NOT NULL,
  INDEX fk_ObserwowanyUzytkownik_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  INDEX fk_ObserwowanyUzytkownik_Uzytkownik2_idx (Uzytkownik_idUzytkownikObserwowany ASC)  ,
  PRIMARY KEY (Uzytkownik_idUzytkownik, Uzytkownik_idUzytkownikObserwowany),
  CONSTRAINT fk_ObserwowanyUzytkownik_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT fk_ObserwowanyUzytkownik_Uzytkownik2
    FOREIGN KEY (Uzytkownik_idUzytkownikObserwowany)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`Post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`Post` (
  idPost INT NOT NULL AUTO_INCREMENT,
  Tagi VARCHAR(400) NOT NULL,
  Opis VARCHAR(1700) NOT NULL,
  DataZamieszczenia DATETIME NOT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  PRIMARY KEY (idPost),
  INDEX fk_Post_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  CONSTRAINT fk_Post_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`Reakcja`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`Reakcja` (
  idReakcja INT NOT NULL AUTO_INCREMENT,
  Uzytkownik_idUzytkownik INT NOT NULL,
  Post_idPost INT NOT NULL,
  PRIMARY KEY (idReakcja),
  INDEX fk_Reakcja_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  INDEX fk_Reakcja_Post1_idx (Post_idPost ASC)  ,
  CONSTRAINT fk_Reakcja_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Reakcja_Post1
    FOREIGN KEY (Post_idPost)
    REFERENCES Inzynierka.`Post` (idPost)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`Ban`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`Ban` (
  idBan INT NOT NULL AUTO_INCREMENT,
  DataZakonczenia DATETIME NOT NULL,
  Powod VARCHAR(100) NOT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  PRIMARY KEY (idBan),
  INDEX fk_Ban_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  CONSTRAINT fk_Ban_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`ZablokowanyUzytkownik`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`ZablokowanyUzytkownik` (
  Uzytkownik_idUzytkownik INT NOT NULL,
  Uzytkownik_idUzytkownikBlokowany INT NOT NULL,
  INDEX fk_ZablokowanyUzytkownik_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  INDEX fk_ZablokowanyUzytkownik_Uzytkownik2_idx (Uzytkownik_idUzytkownikBlokowany ASC)  ,
  PRIMARY KEY (Uzytkownik_idUzytkownik, Uzytkownik_idUzytkownikBlokowany),
  CONSTRAINT fk_ZablokowanyUzytkownik_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ZablokowanyUzytkownik_Uzytkownik2
    FOREIGN KEY (Uzytkownik_idUzytkownikBlokowany)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`Komentarz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`Komentarz` (
  idKomentarz INT NOT NULL AUTO_INCREMENT,
  DataZamieszczenia DATETIME NOT NULL,
  Tresc VARCHAR(2000) NOT NULL,
  Post_idPost INT NOT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  PRIMARY KEY (idKomentarz),
  INDEX fk_Komentarz_Post1_idx (Post_idPost ASC)  ,
  INDEX fk_Komentarz_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  CONSTRAINT fk_Komentarz_Post1
    FOREIGN KEY (Post_idPost)
    REFERENCES Inzynierka.`Post` (idPost)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Komentarz_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`DaneLogowania`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`DaneLogowania` (
  Email VARCHAR(350) NOT NULL,
  HashHasla VARCHAR(45) NOT NULL,
  SolHasla VARCHAR(45) NOT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  UNIQUE INDEX Email_UNIQUE (Email ASC)  ,
  INDEX fk_DaneLogowania_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  PRIMARY KEY (Uzytkownik_idUzytkownik),
  CONSTRAINT fk_DaneLogowania_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`DaneKontaktowe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`DaneKontaktowe` (
  Imie VARCHAR(85) NOT NULL,
  Nazwisko VARCHAR(100) NOT NULL,
  NrKontaBankowego CHAR(24) NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  UNIQUE INDEX NrKontaBankowego_UNIQUE (NrKontaBankowego ASC)  ,
  INDEX fk_DaneKontaktowe_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  PRIMARY KEY (Uzytkownik_idUzytkownik),
  CONSTRAINT fk_DaneKontaktowe_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`Zdjecie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`Zdjecie` (
  Sciezka VARCHAR(200) NOT NULL,
  CzyNaSprzedaz TINYINT(1) NOT NULL,
  Post_idPost INT NOT NULL,
  INDEX fk_Zdjecie_Post1_idx (Post_idPost ASC)  ,
  PRIMARY KEY (Post_idPost),
  CONSTRAINT fk_Zdjecie_Post1
    FOREIGN KEY (Post_idPost)
    REFERENCES Inzynierka.`Post` (idPost)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`TranzakcjaZakupu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`TranzakcjaZakupu` (
  idTranzakcjaZakupu INT NOT NULL AUTO_INCREMENT,
  Kwota FLOAT NOT NULL,
  DataTranzakcji DATETIME NOT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  Uzytkownik_idUzytkownik1 INT NOT NULL,
  Zdjecie_Sciezka VARCHAR(200) NOT NULL,
  PRIMARY KEY (idTranzakcjaZakupu),
  INDEX fk_TranzakcjaZakupu_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  INDEX fk_TranzakcjaZakupu_Uzytkownik2_idx (Uzytkownik_idUzytkownik1 ASC)  ,
  INDEX fk_TranzakcjaZakupu_Zdjecie1_idx (Zdjecie_Sciezka ASC)  ,
  CONSTRAINT fk_TranzakcjaZakupu_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_TranzakcjaZakupu_Uzytkownik2
    FOREIGN KEY (Uzytkownik_idUzytkownik1)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_TranzakcjaZakupu_Zdjecie1
    FOREIGN KEY (Zdjecie_Sciezka)
    REFERENCES Inzynierka.`Zdjecie` (Sciezka)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`Pinezka`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`Pinezka` (
  idPinezka INT NOT NULL AUTO_INCREMENT,
  SzerokoscGeograficzna DECIMAL(8,6) NOT NULL,
  DlugoscGeograficzna DECIMAL(9,6) NOT NULL,
  OpisPinezki VARCHAR(150) NULL,
  KolorPinezki CHAR(7) NOT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  Zdjecie_Post_idPost INT NULL,
  PRIMARY KEY (idPinezka),
  INDEX fk_Pinezka_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  INDEX fk_Pinezka_Zdjecie1_idx (Zdjecie_Post_idPost ASC)  ,
  CONSTRAINT fk_Pinezka_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Pinezka_Zdjecie1
    FOREIGN KEY (Zdjecie_Post_idPost)
    REFERENCES Inzynierka.`Zdjecie` (Post_idPost)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`Powiadomienie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`Powiadomienie` (
  idPowiadomienie INT NOT NULL AUTO_INCREMENT,
  TrescPowiadomienia VARCHAR(51) NOT NULL,
  LinkTresci VARCHAR(200) NOT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  PRIMARY KEY (idPowiadomienie),
  INDEX fk_Powiadomienie_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  CONSTRAINT fk_Powiadomienie_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`PreferencjePogodowe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`PreferencjePogodowe` (
  Temperatura INT NULL,
  PredkoscWiatru INT NULL,
  StopienZachmurzenia INT NULL,
  PrawdopodobienstwoOpadow INT NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  PRIMARY KEY (Uzytkownik_idUzytkownik),
  INDEX fk_PreferencjePogodowe_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  CONSTRAINT fk_PreferencjePogodowe_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`ZakupioneZdjecie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`ZakupioneZdjecie` (
  idZakupioneZdjecie INT NOT NULL AUTO_INCREMENT,
  Uzytkownik_idUzytkownik INT NOT NULL,
  Zdjecie_Post_idPost INT NOT NULL,
  PRIMARY KEY (idZakupioneZdjecie),
  INDEX fk_ZakupioneZdjecie_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  INDEX fk_ZakupioneZdjecie_Zdjecie1_idx (Zdjecie_Post_idPost ASC)  ,
  CONSTRAINT fk_ZakupioneZdjecie_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ZakupioneZdjecie_Zdjecie1
    FOREIGN KEY (Zdjecie_Post_idPost)
    REFERENCES Inzynierka.`Zdjecie` (Post_idPost)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Inzynierka.`SesjaUzytkownika`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Inzynierka.`SesjaUzytkownika` (
  idSesjaUzytkownika INT NOT NULL AUTO_INCREMENT,
  DataRozpoczeciaSesji DATETIME NOT NULL,
  DataZakonczeniaSesji DATETIME NULL,
  Uzytkownik_idUzytkownik INT NOT NULL,
  PRIMARY KEY (idSesjaUzytkownika),
  INDEX fk_SesjaUzytkownika_Uzytkownik1_idx (Uzytkownik_idUzytkownik ASC)  ,
  CONSTRAINT fk_SesjaUzytkownika_Uzytkownik1
    FOREIGN KEY (Uzytkownik_idUzytkownik)
    REFERENCES Inzynierka.`Uzytkownik` (idUzytkownik)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
