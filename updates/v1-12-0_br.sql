ALTER TABLE examination ADD VOLITGENOTYPY boolean;

delete from genotype where id = 83;
delete from genotype where id = 84;
delete from genotype where id = 86;
delete from genotype where id = 88;
delete from genotype where id = 89;
delete from genotype where id = 90;
delete from genotype where id = 91;
delete from genotype where id = 92;
delete from genotype where id = 93;
delete from genotype where id = 96;
delete from genotype where id = 102;
delete from genotype where id = 103;

update examination set VOLITGENOTYPY = false;
update examination set VOLITGENOTYPY = true where id = 18;
update examination set VOLITGENOTYPY = true where id = 19;

INSERT INTO PUBLIC.OPERATION(ID, JEDNOUDENNE, JEDNOUNAVZOREK, KOD, POPIS) VALUES
(10, TRUE, FALSE, '94973', STRINGDECODE('Vy\u0161et\u0159en\u00ed 2 trombofiln\u00edch mutac\u00ed')),
(11, TRUE, FALSE, '94974', STRINGDECODE('Vy\u0161et\u0159en\u00ed 3 trombofiln\u00edch mutac\u00ed')),
(12, TRUE, FALSE, '94973', STRINGDECODE('Vy\u0161et\u0159en\u00ed 5 trombofiln\u00edch mutac\u00ed'));

update score set body = 5796, pocet = 1, vykon_id = 10 where id = 2;
update score set body = 8060, pocet = 1, vykon_id = 11 where id = 4;
update score set body = 10324, pocet = 1, vykon_id = 12 where id = 6;
update score set body = 5796, pocet = 1, vykon_id = 10 where id = 97;


update appmodul set adresa = STRINGDECODE('Opavsk\u00e1 962/39, 708 00 Ostrava Poruba, tel.: +420 597 437 921') where id = 1;

update examination set aktual = false where id = 57;

INSERT INTO PUBLIC.GENOTYPE(ID, NAZEV, VYCHOZI, VYSETRENI_ID, AUTOCOMPL, PORADI) VALUES
(245, 'GpIa (C807T)', '', 16, '', 1),
(246, 'GpIIIa (L33P)', '', 16, '', 2),
(247, 'GpIa (C807T)', '', 19, '', 1),
(248, 'GpIIIa (L33P)', '', 19, '', 2);

update examination set aktual = false, VYSVETLIVKA = STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela') where id = 16;

INSERT INTO PUBLIC.EXAMINATION(ID, AKTUAL, BODY, CERTIF, NAZEV, POPIS, SLOUPCE, VYSVETLIVKA, ZKRATKA, GENOMAC, TAT, VOLITGENOTYPY) VALUES
(67, false, 0, TRUE, 'BRCA1 a BRCA2', STRINGDECODE('Stanoven\u00ed mutac\u00ed v genech BRCA1 a BRCA2 metodou sekvenov\u00e1n\u00ed nov\u00e9 generace, SOP OV \u010d. 16'), 0, '', 'BRCA', FALSE, 0, FALSE);        