INSERT INTO PUBLIC.USER(ID, ISADMIN, JMENO, PARAFA, PASSWORDHASH, POZICE, USERNAME, MODUL_ID, ISDOCTOR, UVOLNUJE) VALUES
(39, FALSE, STRINGDECODE('Bc. Kl\u00e1ra Banotov\u00e1'), 'KB', '46e6e95a4a45afd802c3d25fcccb26c96dc858e9', 'laborantka', 'kb', 1, TRUE, '');

update user set id=13 where id=39;

INSERT INTO PUBLIC.EXAMINATION(ID, AKTUAL, BODY, CERTIF, NAZEV, POPIS, SLOUPCE, VYSVETLIVKA, ZKRATKA, GENOMAC, TAT) VALUES
(61, TRUE, 0, FALSE, STRINGDECODE('Spin\u00e1ln\u00ed muskul\u00e1rn\u00ed atrofie'), STRINGDECODE('Spin\u00e1ln\u00ed muskul\u00e1rn\u00ed atrofie: delece/duplikace exonu 7 a 8 v genech SMN1 a SMN2'), 1, STRINGDECODE('((+)) - Sledovan\u00fd \u00fasek nen\u00ed deletov\u00e1n, ani duplikov\u00e1n, ((del)) - Sledovan\u00fd \u00fasek je deletov\u00e1n, ((dup)) \u2013 Sledovan\u00fd \u00fasek je duplikov\u00e1n.'), 'SMA', FALSE, 10),
(62, FALSE, 0, FALSE, STRINGDECODE('Stanoven\u00ed f\u00fazn\u00edho genu BCR-ABL'), STRINGDECODE('Stanoven\u00ed f\u00fazn\u00edho genu BCR-ABL (Ph1 chromozom): p\u0159estavby M bcr, m bcr a \u00b5 bcr, metodou RT PCR, SOP OV \u010d. 14'), 0, '', '', FALSE, 0);

-- INSERT INTO PUBLIC.GENOTYPE(ID, NAZEV, VYCHOZI, VYSETRENI_ID, AUTOCOMPL, PORADI) VALUES
-- (198, 'SMN1 exon 7', '', 61, '', 1),
-- (199, 'SMN1 exon 8', '', 61, '', 2),
-- (200, 'SMN2 exon 7', '', 61, '', 3),
-- (201, 'SMN2 exon 8', '', 61, '', 4);