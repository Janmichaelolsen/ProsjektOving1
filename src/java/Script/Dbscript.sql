DROP TABLE rolle;
DROP TABLE tilleggskat;
DROP TABLE trening;
DROP TABLE kategori;
DROP TABLE bruker;

CREATE TABLE trening(
  oktnr        INTEGER NOT NULL,
  dato         DATE NOT NULL,
  varighet     INTEGER,
  kategorinavn VARCHAR(15),
  tekst        VARCHAR(30),
  brukernavn   VARCHAR(10),
  CONSTRAINT trenings_pk PRIMARY KEY(oktnr, brukernavn));
 
CREATE TABLE bruker(
  brukernavn VARCHAR(10) PRIMARY KEY,
  passord    VARCHAR(10) NOT NULL);
 
CREATE TABLE kategori(
  kategorinavn VARCHAR(15)PRIMARY KEY);

CREATE TABLE tilleggskat(
  tilleggkat VARCHAR(15),
  brukernavn VARCHAR(10),
  CONSTRAINT tilleggskat_pk PRIMARY KEY(tilleggkat, brukernavn));

CREATE TABLE rolle(
   brukernavn VARCHAR(10),
   rolle      VARCHAR(10),
   CONSTRAINT rolle_pk PRIMARY KEY(brukernavn, rolle));
  
ALTER TABLE trening
  ADD CONSTRAINT trening_fk1 FOREIGN KEY (kategorinavn)
  REFERENCES kategori;
  
ALTER TABLE trening
  ADD CONSTRAINT trening_fk2 FOREIGN KEY (brukernavn)
  REFERENCES bruker;

ALTER TABLE tilleggskat
  ADD CONSTRAINT trening_fk3 FOREIGN KEY (brukernavn)
  REFERENCES bruker;

ALTER TABLE rolle
  ADD CONSTRAINT rolle_fk1 FOREIGN KEY (brukernavn)
  REFERENCES bruker;


INSERT INTO kategori(kategorinavn) VALUES('Sykling');
INSERT INTO kategori(kategorinavn) VALUES('Styrke');
INSERT INTO kategori(kategorinavn) VALUES('Svømming');
INSERT INTO kategori(kategorinavn) VALUES('Løping');
INSERT INTO kategori(kategorinavn) VALUES('Yoga');
INSERT INTO kategori(kategorinavn) VALUES('Biking');
INSERT INTO kategori(kategorinavn) VALUES('Strength');
INSERT INTO kategori(kategorinavn) VALUES('Swimming');
INSERT INTO kategori(kategorinavn) VALUES('Running');

INSERT INTO bruker(brukernavn, passord) VALUES('anne', 'xyz_1b');
INSERT INTO bruker(brukernavn, passord) VALUES('tore', 'xcg_5');
INSERT INTO bruker(brukernavn, passord) VALUES('as','df');

INSERT INTO rolle(brukernavn, rolle) VALUES('anne', 'bruker');
INSERT INTO rolle(brukernavn, rolle) VALUES('tore', 'bruker');
INSERT INTO rolle(brukernavn, rolle) VALUES('as', 'bruker');
