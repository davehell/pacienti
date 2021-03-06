DROP TABLE IF EXISTS PUBLIC.EXAMINATION CASCADE;
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_6B43B2EE_4FAD_43A7_8D1C_48AD609292FF START WITH 58 BELONGS_TO_TABLE;
CREATE CACHED TABLE PUBLIC.EXAMINATION(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_6B43B2EE_4FAD_43A7_8D1C_48AD609292FF) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_6B43B2EE_4FAD_43A7_8D1C_48AD609292FF,
    AKTUAL BOOLEAN NOT NULL,
    BODY INTEGER NOT NULL,
    CERTIF BOOLEAN NOT NULL,
    NAZEV VARCHAR(255),
    POPIS CLOB,
    SLOUPCE INTEGER NOT NULL,
    VYSVETLIVKA VARCHAR(255)
);
ALTER TABLE PUBLIC.EXAMINATION ADD CONSTRAINT PUBLIC.CONSTRAINT_9 PRIMARY KEY(ID);
-- 25 +/- SELECT COUNT(*) FROM PUBLIC.EXAMINATION;
INSERT INTO PUBLIC.EXAMINATION(ID, AKTUAL, BODY, CERTIF, NAZEV, POPIS, SLOUPCE, VYSVETLIVKA) VALUES
(1, FALSE, 0, FALSE, STRINGDECODE('Predispozice k \u017eiln\u00edm tromb\u00f3z\u00e1m'), 'Factor V Leiden (G1691A), Factor V R2 (H1299R), Factor II protrombin (G20210A), MTHFR (C677T), MTHFR (A1298C), Factor VIII (V34L), PAI-1 (4G/5G), EPCR (A4600G), EPCR (G4678C)', 1, NULL),
(2, TRUE, 0, FALSE, STRINGDECODE('Predispozice k ateroskler\u00f3ze'), STRINGDECODE('eNOS (-786T>C), eNOS (G894T), HPA1 a/b - GPIIIa (l33P), ACE (Ins/Del), Apo B (R3500Q), Apo E (alely E2, E3, E4), LTA (C804A), FGB - \u00df-fibrinogen (-455G>A)'), 1, ''),
(3, TRUE, 0, FALSE, STRINGDECODE('Hemochromat\u00f3za - porucha metabolizmu \u017eeleza'), 'mutace v genu HFE (C282Y, H63D, S65C)', 1, ''),
(4, TRUE, 11170, FALSE, 'Wilsonova choroba', STRINGDECODE('Stanoven\u00ed mutac\u00ed v exonech 8, 14, 15 genu ATP7B - Morbus Wilson, SOP OV \u010d. 10'), 1, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(5, TRUE, 0, FALSE, STRINGDECODE('D\u011bdi\u010dn\u011b podm\u00edn\u011bn\u00e1 odpov\u011b\u010f organizmu na l\u00e9\u010dbu warfarinem'), 'polymorfizmus v cytochromu P450 CYP2C9 (C430T, A1075C) a VKORC1 (-1639G>A)', 1, ''),
(7, TRUE, 21138, TRUE, STRINGDECODE('Cystick\u00e1 fibr\u00f3za'), STRINGDECODE('Stanoven\u00ed mutac\u00ed v CFTR genu (33 mutac\u00ed + Tn) SOP OV \u010d. 2 *'), 4, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(8, TRUE, 12243, TRUE, 'Delece v AZF genu', STRINGDECODE('Stanoven\u00ed mikrodelece ve faktoru AZF a/b/c lokalizovan\u00e9m na Y chromozomu, SOP OV \u010d. 4 *'), 1, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(9, TRUE, 0, FALSE, STRINGDECODE('Achondropl\u00e1zie'), 'mutace v genu FGFR-3 (G380R)', 1, ''),
(10, FALSE, 0, FALSE, STRINGDECODE('CS - abnorm\u00e1ln\u00ed imunitn\u00ed odpove\u010f na lepek'), STRINGDECODE('HLA alely II. T\u0159\u00eddy (DRB104, DQA 10501, DQB10201/0202)'), 1, NULL),
(11, TRUE, 4214, FALSE, STRINGDECODE('Lakt\u00f3zov\u00e1 intolerance'), STRINGDECODE('Stanoven\u00ed  lakt\u00f3zov\u00e9  intolerance \u2013 mutac\u00ed -13910T/C a -22018A/G v genu LCT, SOP OV \u010d.7'), 1, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(12, TRUE, 11170, FALSE, STRINGDECODE('Prelingu\u00e1ln\u00ed hluchota'), STRINGDECODE('Stanoven\u00ed mutac\u00ed v k\u00f3duj\u00edc\u00ed sekvenci genu pro Connexin 26 (GJB 2) a IVS 1+1 G>A SOP OV \u010d. 9'), 1, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(13, TRUE, 4076, FALSE, STRINGDECODE('Becht\u011brevova choroba-HLA B27'), STRINGDECODE('Stanoven\u00ed exprese genu HLA B 27 asociovan\u00e9ho s revmatick\u00fdmi chorobami - Becht\u011brevova nemoc, SOP OV \u010d. 6'), 1, STRINGDECODE('negativn\u00ed = HLA typ nenalezen, pozitivn\u00ed = HLA typ nalezen')),
(14, TRUE, 6523, FALSE, STRINGDECODE('Gilbert\u016fv syndrom'), STRINGDECODE('GILBERT\u016eV SYNDROM \u2013 stanoven\u00ed inzerce A(TA)7 TAA  v promotoru genu UGT1A1 metodou PCR, SOP OV \u010d. 8'), 1, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(15, TRUE, 6070, TRUE, STRINGDECODE('Celiak\u00e1ln\u00ed sprue'), STRINGDECODE('Stanoven\u00ed vybran\u00fdch alel HLA II. t\u0159\u00eddy asociovan\u00fdch s rozvojem celiak\u00e1ln\u00ed sprue, SOP OV \u010d. 3 *'), 1, STRINGDECODE('negativn\u00ed = HLA typ nenalezen, pozitivn\u00ed = HLA typ nalezen')),
(16, FALSE, 0, FALSE, 'Glykoproteiny', 'GPIa, GPIIIa', 1, NULL),
(17, TRUE, 7205, TRUE, STRINGDECODE('Predispozice k \u017eiln\u00edm tromb\u00f3z\u00e1m I'), STRINGDECODE('Stanoven\u00ed trombofiln\u00edch mutac\u00ed metodou Real-Time PCR *Factor V Leiden (G1691A), *Factor V R2 (H1299R), *Factor II (G20210A), *MTHFR (C677T), *MTHFR (A1298C), *Factor XIII (V34L), *PAI-1 (4G/5G), *EPCR (A4600G), *EPCR (G4678C), GpIa(C807T) a GpIIIa (T1565C), SOP OV \u010d.1'), 2, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela'));
INSERT INTO PUBLIC.EXAMINATION(ID, AKTUAL, BODY, CERTIF, NAZEV, POPIS, SLOUPCE, VYSVETLIVKA) VALUES
(18, TRUE, 11193, TRUE, STRINGDECODE('Predispozice k \u017eiln\u00edm tromb\u00f3z\u00e1m II'), STRINGDECODE('Stanoven\u00ed trombofiln\u00edch mutac\u00ed metodou Real-Time PCR *Factor V Leiden (G1691A), *Factor V R2 (H1299R), *Factor II (G20210A), *MTHFR (C677T), *MTHFR (A1298C), *Factor XIII (V34L), *PAI-1 (4G/5G), *EPCR (A4600G), *EPCR (G4678C), GpIa(C807T) a GpIIIa (T1565C), SOP OV \u010d.1'), 2, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(19, TRUE, 12190, TRUE, STRINGDECODE('Predispozice k \u017eiln\u00edm tromb\u00f3z\u00e1m III'), STRINGDECODE('Stanoven\u00ed trombofiln\u00edch mutac\u00ed metodou Real-Time PCR *Factor V Leiden (G1691A), *Factor V R2 (H1299R), *Factor II (G20210A), *MTHFR (C677T), *MTHFR (A1298C), *Factor XIII (V34L), *PAI-1 (4G/5G), *EPCR (A4600G), *EPCR (G4678C), GpIa(C807T) a GpIIIa (T1565C), SOP OV \u010d.1'), 2, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(20, FALSE, 0, FALSE, 'foo', 'foo', 0, NULL),
(21, TRUE, 6208, FALSE, 'Crohnova choroba', STRINGDECODE('Stanoven\u00ed mutac\u00ed  R702W, G908R a 3020 ins C v genu NOD 2/CARD 15  Crohnova choroba, SOP OV \u010d. 11'), 1, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela')),
(22, TRUE, 0, FALSE, 'EGFR', STRINGDECODE('Stanoven\u00ed mutac\u00ed v genu EGFR, SOP \u010d. 032'), 1, STRINGDECODE('Negat. \u2013 negativn\u00ed = wild type = wt; ((O)); Pozit. \u2013 pozitivn\u00ed = mut; ((X)); Inkonkluz. - inkonkluzivn\u00ed = ((ink))')),
(23, TRUE, 0, FALSE, 'K RAS', STRINGDECODE('Stanoven\u00ed mutac\u00ed v genu K RAS, SOP  \u010d. 034'), 1, STRINGDECODE('Negat. \u2013 negativn\u00ed = wild type = wt; ((O)); Pozit. \u2013 pozitivn\u00ed = mut; ((X)); Inkonkluz. - inkonkluzivn\u00ed = ((ink))')),
(24, TRUE, 0, FALSE, 'JAK-2', STRINGDECODE('JAK-2 (V617F), SOP OV \u010d.12'), 1, STRINGDECODE('Negat. \u2013 negativn\u00ed = mutace V617F nenalezena, Pozit. \u2013 pozitivn\u00ed= mutace V617F nalezena')),
(56, TRUE, 0, FALSE, 'GpIa (C807T)', 'GpIa (C807T)', 1, ''),
(57, TRUE, 0, FALSE, 'GpIIIa (L33P)', 'GpIIIa (L33P)', 1, '');



DROP TABLE IF EXISTS PUBLIC.GENOTYPE CASCADE;
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_99D859D6_0264_4DA2_BBC3_930EABB041E7 START WITH 173 BELONGS_TO_TABLE;
CREATE CACHED TABLE PUBLIC.GENOTYPE(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_99D859D6_0264_4DA2_BBC3_930EABB041E7) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_99D859D6_0264_4DA2_BBC3_930EABB041E7,
    NAZEV VARCHAR(255),
    VYCHOZI VARCHAR(255),
    VYSETRENI_ID BIGINT
);
ALTER TABLE PUBLIC.GENOTYPE ADD CONSTRAINT PUBLIC.CONSTRAINT_9B PRIMARY KEY(ID);
-- 129 +/- SELECT COUNT(*) FROM PUBLIC.GENOTYPE;
INSERT INTO PUBLIC.GENOTYPE(ID, NAZEV, VYCHOZI, VYSETRENI_ID) VALUES
(1, 'Factor V Leiden (G1691A)', '', 1),
(3, 'Factor V R2 (H1299R)', '', 1),
(4, 'Factor II protrombin (G20210A)', '', 1),
(5, 'MTHFR (C677T)', '', 1),
(6, 'MTHFR (A1298C)', '', 1),
(7, 'Factor XIII (V34L)', '', 1),
(8, 'PAI-1 (4G/5G)', '', 1),
(9, 'EPCR (A4600G / G4678C)', '', 1),
(10, 'EPCR (G4678C)', '', 1),
(11, 'eNOS (-786T>C)', '', 2),
(12, 'eNOS (G894T)', '', 2),
(13, 'HPA1 a/b - GPIIIa (l33P)', '', 2),
(14, 'ACE (Ins/Del)', '', 2),
(15, 'Apo B (R3500Q)', '', 2),
(16, 'Apo E (alely E2, E3, E4)', '', 2),
(17, 'LTA (C804A)', '', 2),
(18, STRINGDECODE('FGB - \u03b2-fibrinogen (-455G>A)'), '', 2),
(19, 'C282Y, mutace', '', 3),
(20, 'H63D, mutace', '', 3),
(21, 'S65C, mutace', '', 3),
(22, STRINGDECODE('ATP7B (k\u00f3duj\u00edc\u00ed sekvence exonu 8)'), '', 4),
(23, STRINGDECODE('ATP7B (k\u00f3duj\u00edc\u00ed sekvence exonu 14)'), '', 4),
(24, STRINGDECODE('ATP7B (k\u00f3duj\u00edc\u00ed sekvence exonu 15)'), '', 4),
(25, '430C/T, polymorfismus', '', 5),
(26, '1075A/C, polymorfismus', '', 5),
(27, '(-)1639G/A', '', 5),
(28, 'F508del', '', 7),
(30, 'G85E', '', 7),
(31, '3659delC', '', 7),
(32, 'I507del', '', 7),
(33, '3905insT', '', 7),
(34, '621+1G->T', '', 7),
(35, '3849+10KbC->T', '', 7),
(36, 'G542X', '', 7),
(37, 'CFTRdele2,3 (21 kb)', '', 7),
(38, 'R117H', '', 7),
(39, '2143delT', '', 7),
(40, '1717-1G->A', '', 7),
(41, '711+1G->T', '', 7),
(42, '1078delT', '', 7),
(43, 'A455E', '', 7),
(44, 'G551D', '', 7),
(45, '3272-26A->G', '', 7),
(46, 'R347P', '', 7),
(47, '2183AA->G', '', 7),
(48, 'R553X', '', 7),
(49, '1898+1G->A', '', 7),
(50, 'R334W', '', 7),
(51, '2184delA', '', 7),
(52, 'R560T', '', 7),
(55, 'Tn', '', 7),
(56, 'N1303K', '', 7),
(60, '3120+1G->A', '', 7),
(61, '2789+5G->A', '', 7),
(62, 'W1282X', '', 7),
(63, '394delTT', '', 7),
(64, 'R1162X', '', 7),
(65, 'AZFa: sY84 / sY86', '', 8),
(66, 'AZFb: sY127 /  sY134', '', 8),
(67, 'AZFc: sY254 /  sY255', '', 8),
(69, 'G308R, mutace', '', 9),
(70, 'DQA1* 05', '', 10),
(71, 'DQB1* 02', '', 10),
(72, 'DRB1* 04', '', 10),
(73, '(-)13910T/C', '', 11),
(74, '(-)22018A/G', '', 11),
(75, 'del4E4, mutace', '', 20),
(76, 'A149P, mutace', '', 20),
(77, 'A174D, mutace', '', 20),
(78, 'N334K, mutace', '', 20),
(79, STRINGDECODE('GJB2 (k\u00f3duj\u00edc\u00ed sekvence v exonu 2)'), '', 12),
(80, 'IVS 1+1 G->A', '', 12),
(81, 'HLA B27', '', 13),
(82, STRINGDECODE('Inzerce  TA repetitivn\u00ed sekvence (7TA)'), '', 14),
(83, 'Gp Ia (C 807 T)', '', 1),
(84, 'Gp IIIa (T 1565 C)', '', 1),
(85, 'DQA1*05 / DQB1*02', '', 10),
(86, 'DQA1*03 / DQB1*03', '', 10),
(88, 'HLA typ DQ2 (DQA1*05/DQB1*02)', '', 15),
(89, 'HLA typ DQ8 (DQA1*03/DQB1*03)', '', 15),
(91, 'GpIa', '', 16),
(92, 'GpIIIa', '', 16),
(93, 'Factor V Leiden (G1691A)', '', 17),
(94, 'MTHFR (A1298C)', '', 17),
(95, 'Factor V R2 (H1299R)', '---', 17),
(96, 'Factor XIII (V34L)', '---', 17),
(97, 'Factor II (G20210A)', '', 17),
(98, 'PAI-1 (4G/5G)', '---', 17),
(99, 'MTHFR (C677T)', '', 17),
(100, 'EPCR (A4600G)-A3', '---', 17),
(101, 'EPCR (G4678C)-A1', '---', 17),
(102, 'GpIa (C807T)', '---', 17),
(103, 'GpIIIa (T1565C)', '---', 17),
(104, 'Factor V Leiden (G1691A)', '', 18),
(105, 'MTHFR (A1298C)', '', 18),
(106, 'Factor V R2 (H1299R)', '', 18),
(107, 'Factor XIII (V34L)', '', 18),
(108, 'Factor II (G20210A)', '', 18),
(109, 'PAI-1 (4G/5G)', '', 18),
(110, 'MTHFR (C677T)', '', 18),
(111, 'EPCR (A4600G)-A3', '', 18),
(112, 'EPCR (G4678C)-A1', '', 18),
(115, 'Factor V Leiden (G1691A)', '', 19),
(116, 'MTHFR (A1298C)', '', 19),
(117, 'Factor V R2 (H1299R)', '', 19),
(118, 'Factor XIII (V34L)', '', 19),
(119, 'Factor II (G20210A)', '', 19),
(120, 'PAI-1 (4G/5G)', '', 19),
(121, 'MTHFR (C677T)', '', 19),
(122, 'EPCR (A4600G)-A3', '', 19),
(123, 'EPCR (G4678C)-A1', '', 19),
(124, 'GpIa (C807T)', '', 19),
(125, 'GpIIIa (T1565C)', '', 19),
(126, 'R702W', '', 21),
(127, 'G908R', '', 21),
(128, '3020 ins C', '', 21),
(129, 'EGFR exon 19', '', 22),
(130, 'EGFR exon 21', '', 22),
(131, 'K RAS', '', 23),
(132, 'EGFR exon 19', '', 22),
(133, 'EGFR exon 21', '', 22),
(134, '2184insA', '', 7);
INSERT INTO PUBLIC.GENOTYPE(ID, NAZEV, VYCHOZI, VYSETRENI_ID) VALUES
(135, 'Y122X', '', 7),
(136, 'Y1092X', '', 7),
(137, 'R1162X', '', 7),
(138, 'V617F', '', 24),
(170, 'GpIa (C807T)', '', 56),
(171, 'GpIIIa (L33P)', '', 57),
(172, 'R347H', '', 7);

ALTER TABLE PUBLIC.GENOTYPE ADD CONSTRAINT PUBLIC.FK7050A6F9858967A FOREIGN KEY(VYSETRENI_ID) REFERENCES PUBLIC.EXAMINATION(ID) NOCHECK;




DROP TABLE IF EXISTS PUBLIC.SCORE CASCADE;
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_E1770716_79A3_4FD4_9C9A_91C61C54CC05 START WITH 93 BELONGS_TO_TABLE;
CREATE CACHED TABLE PUBLIC.SCORE(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_E1770716_79A3_4FD4_9C9A_91C61C54CC05) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_E1770716_79A3_4FD4_9C9A_91C61C54CC05,
    BODY INTEGER NOT NULL,
    JEDNOUDENNE BOOLEAN NOT NULL,
    JEDNOUNAVZOREK BOOLEAN NOT NULL,
    KOD VARCHAR(255),
    POPIS VARCHAR(255),
    POCET INTEGER NOT NULL,
    VYKON_ID BIGINT,
    VYSETRENI_ID BIGINT
);
ALTER TABLE PUBLIC.SCORE ADD CONSTRAINT PUBLIC.CONSTRAINT_4B PRIMARY KEY(ID);
-- 62 +/- SELECT COUNT(*) FROM PUBLIC.SCORE;
INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, KOD, POPIS, POCET, VYKON_ID, VYSETRENI_ID) VALUES
(1, 0, FALSE, FALSE, NULL, NULL, 1, 1, 17),
(2, 0, FALSE, FALSE, NULL, NULL, 6, 2, 17),
(3, 0, FALSE, FALSE, NULL, NULL, 1, 1, 18),
(4, 0, FALSE, FALSE, NULL, NULL, 10, 2, 18),
(5, 0, FALSE, FALSE, NULL, NULL, 1, 1, 19),
(6, 0, FALSE, FALSE, NULL, NULL, 11, 2, 19),
(7, 0, FALSE, FALSE, NULL, NULL, 1, 1, 7),
(8, 0, FALSE, FALSE, NULL, NULL, 6, 2, 7),
(9, 0, FALSE, FALSE, NULL, NULL, 1, 3, 7),
(10, 0, FALSE, FALSE, NULL, NULL, 1, 4, 7),
(11, 0, FALSE, FALSE, NULL, NULL, 4, 5, 7),
(12, 0, FALSE, FALSE, NULL, NULL, 1, 1, 12),
(13, 0, FALSE, FALSE, NULL, NULL, 3, 6, 12),
(14, 0, FALSE, FALSE, NULL, NULL, 1, 7, 12),
(15, 0, FALSE, FALSE, NULL, NULL, 3, 2, 12),
(16, 0, FALSE, FALSE, NULL, NULL, 1, 1, 4),
(17, 0, FALSE, FALSE, NULL, NULL, 3, 6, 4),
(18, 0, FALSE, FALSE, NULL, NULL, 1, 7, 4),
(19, 0, FALSE, FALSE, NULL, NULL, 3, 2, 4),
(20, 0, FALSE, FALSE, NULL, NULL, 1, 1, 14),
(21, 0, FALSE, FALSE, NULL, NULL, 2, 6, 14),
(22, 0, FALSE, FALSE, NULL, NULL, 1, 7, 14),
(23, 0, FALSE, FALSE, NULL, NULL, 1, 1, 11),
(24, 0, FALSE, FALSE, NULL, NULL, 3, 2, 11),
(25, 0, FALSE, FALSE, NULL, NULL, 1, 1, 15),
(26, 0, FALSE, FALSE, NULL, NULL, 4, 2, 15),
(27, 0, FALSE, FALSE, NULL, NULL, 1, 3, 15),
(28, 0, FALSE, FALSE, NULL, NULL, 1, 4, 15),
(29, 0, FALSE, FALSE, NULL, NULL, 1, 1, 13),
(30, 0, FALSE, FALSE, NULL, NULL, 2, 2, 13),
(31, 0, FALSE, FALSE, NULL, NULL, 2, 3, 13),
(32, 0, FALSE, FALSE, NULL, NULL, 1, 4, 13),
(33, 0, FALSE, FALSE, NULL, NULL, 1, 1, 8),
(34, 0, FALSE, FALSE, NULL, NULL, 5, 6, 8),
(65, 0, FALSE, FALSE, NULL, NULL, 1, 1, 22),
(66, 0, FALSE, FALSE, NULL, NULL, 3, 6, 22),
(67, 0, FALSE, FALSE, NULL, NULL, 6, 7, 22),
(68, 0, FALSE, FALSE, NULL, NULL, 1, 1, 23),
(69, 0, FALSE, FALSE, NULL, NULL, 2, 6, 23),
(70, 0, FALSE, FALSE, NULL, NULL, 4, 7, 23),
(71, 0, FALSE, FALSE, NULL, NULL, 1, 1, 3),
(72, 0, FALSE, FALSE, NULL, NULL, 3, 2, 3),
(73, 0, FALSE, FALSE, NULL, NULL, 1, 3, 3),
(74, 0, FALSE, FALSE, NULL, NULL, 1, 4, 3),
(75, 0, FALSE, FALSE, NULL, NULL, 2, 5, 3),
(76, 0, FALSE, FALSE, NULL, NULL, 1, 1, 5),
(77, 0, FALSE, FALSE, NULL, NULL, 3, 2, 5),
(78, 0, FALSE, FALSE, NULL, NULL, 1, 3, 5),
(79, 0, FALSE, FALSE, NULL, NULL, 1, 4, 5),
(80, 0, FALSE, FALSE, NULL, NULL, 1, 5, 5),
(81, 0, FALSE, FALSE, NULL, NULL, 1, 1, 9),
(82, 0, FALSE, FALSE, NULL, NULL, 6, 2, 9),
(83, 0, FALSE, FALSE, NULL, NULL, 4, 8, 9),
(84, 0, FALSE, FALSE, NULL, NULL, 2, 3, 9),
(85, 0, FALSE, FALSE, NULL, NULL, 2, 4, 9),
(86, 0, FALSE, FALSE, NULL, NULL, 1, 1, 2),
(87, 0, FALSE, FALSE, NULL, NULL, 3, 6, 2),
(88, 0, FALSE, FALSE, NULL, NULL, 1, 5, 2),
(89, 0, FALSE, FALSE, NULL, NULL, 1, 1, 21),
(90, 0, FALSE, FALSE, NULL, NULL, 5, 2, 21),
(91, 0, FALSE, FALSE, NULL, NULL, 1, 1, 24),
(92, 0, FALSE, FALSE, NULL, NULL, 6, 2, 24);

ALTER TABLE PUBLIC.SCORE ADD CONSTRAINT PUBLIC.FK4C04E72D6831536 FOREIGN KEY(VYKON_ID) REFERENCES PUBLIC.OPERATION(ID) NOCHECK;
ALTER TABLE PUBLIC.SCORE ADD CONSTRAINT PUBLIC.FK4C04E72858967A FOREIGN KEY(VYSETRENI_ID) REFERENCES PUBLIC.EXAMINATION(ID) NOCHECK;


update appmodul set adresa='Opavská 962/39, 708 00 Ostrava-Poruba, tel.: +420 596 973 356' where nazev='Ostrava';
update appmodul set adresa='Tvrdého 2a, 602 00 Brno, tel.: +420 543 426 511' where nazev='Brno';

