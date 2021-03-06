-- BRNO
update examination set id=56 where id=58;

ALTER TABLE examination ADD genomac boolean DEFAULT 'false';

INSERT INTO PUBLIC.EXAMINATION(ID, AKTUAL, BODY, CERTIF, NAZEV, POPIS, SLOUPCE, VYSVETLIVKA, ZKRATKA, GENOMAC) VALUES
(57, false, 4251, TRUE, STRINGDECODE('Predispozice k \u017eiln\u00edm tromb\u00f3z\u00e1m IV'), STRINGDECODE('Stanoven\u00ed trombofiln\u00edch mutac\u00ed metodou real-time PCR (SOP OV \u010d. 1)'), 2, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela'), 'TC4', FALSE),
(58, false, 10194, FALSE, 'NRAS', STRINGDECODE('Stanoven\u00ed mutac\u00ed v genu NRAS, v exonech 2, 3 a 4 metodou PCR'), 1, STRINGDECODE('Negat. \u2013 negativn\u00ed = wild type = wt; ((O)); Pozit. \u2013 pozitivn\u00ed = mut; ((X)); Inkonkluz. - inkonkluzivn\u00ed = ((ink))'), 'NRAS', false),
(59, false, 0, FALSE, 'BCR/ABL', STRINGDECODE('Stanoven\u00ed f\u00fazn\u00edho genu BCR/ABL z RNA metodou RT-PCR,<br>SOP OV \u010d. 14'), 1, '', 'BCR/ABL', FALSE),
(60, false, 10194, FALSE, 'KRAS 1', STRINGDECODE('Stanoven\u00ed mutac\u00ed v genu KRAS metodou PCR<sup>*G</sup>'), 1, STRINGDECODE('Negat. \u2013 negativn\u00ed = wild type = wt; ((O)); Pozit. \u2013 pozitivn\u00ed = mut; ((X)); Inkonkluz. - inkonkluzivn\u00ed = ((ink))'), 'KRAS1', false);

ALTER TABLE genotype ADD autocompl varchar(255) DEFAULT '';
ALTER TABLE genotype ADD poradi int DEFAULT 0;

truncate table genotype;

INSERT INTO PUBLIC.GENOTYPE(ID, NAZEV, VYCHOZI, VYSETRENI_ID, PORADI, AUTOCOMPL) VALUES
(1, 'eNOS (-786T>C)' , '' , 2, 1, 'wt/wt,mut/wt,mut/mut'),
(2, 'eNOS (G894T)' , '' , 2, 2, 'wt/wt,mut/wt,mut/mut'),
(3, 'LTA (C804A)' , '' , 2, 3, 'wt/wt,mut/wt,mut/mut'),
(4, 'ACE (Ins/Del)' , '' , 2, 4, 'del/del,ins/del,ins/ins'),
(5, 'HPA1 a/b - GPIIIa (L33P)' , '' , 2, 5, '1a/1a,1a/1b,1b/1b'),
(6, 'FGB - ß-fibrinogen (-455G>A)' , '' , 2, 6, 'wt/wt,mut/wt,mut/mut'),
(7, 'Apo B (R3500Q)' , '' , 2, 7, 'wt/wt,mut/wt,mut/mut'),
(8, 'Apo E (alely E2, E3, E4)' , '' , 2, 8, 'E2/E2,E2/E3,E2/E4,E3/E3,E3/E4,E4/E4'),

(9, 'H63D' , '' , 3, 1, 'wt/wt,mut/wt,mut/mut'),
(10, 'S65C' , '' , 3, 2, 'wt/wt,mut/wt,mut/mut'),
(11, 'C282Y' , '' , 3, 3, 'wt/wt,mut/wt,mut/mut'),

(12, 'ATP7B (kódující sekvence exonu 8)' , '' , 4, 1, ''),
(13, 'ATP7B (kódující sekvence exonu 14)' , '' , 4, 2, ''),
(14, 'ATP7B (kódující sekvence exonu 15)' , '' , 4, 3, ''),

(15, 'VKORC1 (-16396>A)' , '' , 5, 1, 'wt/wt,mut/wt,mut/mut'),
(16, 'CYP2C9: I359L (A1075C)' , '' , 5, 2, 'wt/wt,mut/wt,mut/mut'),
(17, 'CYP2C9: R144C (C430T)' , '' , 5, 3, 'wt/wt,mut/wt,mut/mut'),

(18, 'CFTRdele2,3 (21 kb)' , 'wt/wt' , 7, 1, ''),
(19, 'I507del' , 'wt/wt' , 7, 2, ''),
(20, 'F508del' , 'wt/wt' , 7, 3, ''),
(21, '1717-1G->A' , 'wt/wt' , 7, 4, ''),
(22, 'G542X' , 'wt/wt' , 7, 5, ''),
(23, 'G551D' , 'wt/wt' , 7, 6, ''),
(24, 'R553X' , 'wt/wt' , 7, 7, ''),
(25, 'R560T' , 'wt/wt' , 7, 8, ''),
(26, '2143delT' , 'wt/wt' , 7, 9, ''),
(27, '2183AA->G' , 'wt/wt' , 7, 10, ''),
(28, '2184delA' , 'wt/wt' , 7, 11, ''),
(29, '2184insA' , 'wt/wt' , 7, 12, ''),
(30, '2789+5G->A' , 'wt/wt' , 7, 13, ''),
(31, 'R1162X' , 'wt/wt' , 7, 14, ''),
(32, '3659delC' , 'wt/wt' , 7, 15, ''),
(33, '3905insT' , 'wt/wt' , 7, 16, ''),
(34, 'W1282X' , 'wt/wt' , 7, 17, ''),
(35, 'N1303K' , 'wt/wt' , 7, 18, ''),
(36, 'G85E' , 'wt/wt' , 7, 19, ''),
(37, '394delTT' , 'wt/wt' , 7, 20, ''),
(38, 'R117H' , 'wt/wt' , 7, 21, ''),
(39, 'Y122X' , 'wt/wt' , 7, 22, ''),
(40, '621+1G->T' , 'wt/wt' , 7, 23, ''),
(41, '711+1G->T' , 'wt/wt' , 7, 24, ''),
(42, '1078delT' , 'wt/wt' , 7, 25, ''),
(43, 'R334W' , 'wt/wt' , 7, 26, ''),
(44, 'R347H' , 'wt/wt' , 7, 27, ''),
(45, 'R347P' , 'wt/wt' , 7, 28, ''),
(46, 'A455E' , 'wt/wt' , 7, 29, ''),
(47, '1898+1G->A' , 'wt/wt' , 7, 30, ''),
(48, '3120+1G->A' , 'wt/wt' , 7, 31, ''),
(49, '3272-26A->G' , 'wt/wt' , 7, 32, ''),
(50, 'Y1092X' , 'wt/wt' , 7, 33, ''),
(51, '3849+10KbC->T' , 'wt/wt' , 7, 34, ''),
(52, 'IVS8' , '' , 7, 35, '7T/7T,7T/9T,9T/9T,5T/7T,5T/9T'),

(53, 'sY84 (oblast AZFa)' , '' , 8, 1, ''),
(54, 'sY86 (oblast AZFa)' , '' , 8, 2, ''),
(55, 'sY134 (oblast AZFb)' , '' , 8, 3, ''),
(56, 'sY127 (oblast AZFb)' , '' , 8, 4, ''),
(57, 'sY255 (oblast AZFc)' , '' , 8, 5, ''),
(58, 'sY254 (oblast AZFc)' , '' , 8, 6, ''),

(59, 'FGFR3A (G1138A)' , '' , 9, 1, ''),
(60, 'FGFR3C (G1138C)' , '' , 9, 2, ''),

(61, '(-)13910C/T' , '' , 11, 1, ''),
(62, '(-)22018G/A' , '' , 11, 2, ''),
(63, 'GJB2 (kódující sekvence v exonu 2)' , '' , 12, 1, ''),
(64, 'IVS 1+1 G->A' , '' , 12, 2, ''),
(65, 'HLA B27' , '' , 13, 1, ''),
(66, 'Inzerce TA repetitivní sekvence (7TA)', '' , 14, 1, ''),
(67, 'DQA1*', '', 15, 1, ''),
(68, 'DQB1*', '', 15, 2, ''),
(69, 'DQA1*', '', 15, 3, ''),
(70, 'DQB1*', '', 15, 4, ''),

(71, 'Factor V Leiden (G1691A)*' , '' , 17, 1, 'wt/wt,mut/wt,mut/mut'),
(72, 'MTHFR (A1298C)*' , '' , 17, 2, 'wt/wt,mut/wt,mut/mut'),
(73, 'MTHFR (C677T)*' , '' , 17, 3, 'wt/wt,mut/wt,mut/mut'),
(74, 'Factor II (G20210A)*' , '' , 17, 4, 'wt/wt,mut/wt,mut/mut'),
(75, 'Factor V R2 (H1299R)*' , '---' , 17, 5, 'wt/wt,mut/wt,mut/mut'),
(76, 'PAI-1 (4G/5G)*' , '---' , 17, 6, '5G/5G,4G/5G,4G/4G'),
(77, 'Factor XIII (V34L)*' , '---' , 17, 7, 'wt/wt,mut/wt,mut/mut'),
(78, 'EPCR (A4600G)*' , '---' , 17, 8, 'A/A,G/A,G/G'),
(79, 'EPCR (G4678C)*' , '---' , 17, 9, 'G/G,C/G,C/C'),
(80, 'GpIa (C807T)' , '---' , 17, 10, ''),
(81, 'GpIIIa (T1565C)' , '---' , 17, 11, ''),

(82, 'Factor V Leiden (G1691A)*' , '' , 18, 1, 'wt/wt,mut/wt,mut/mut'),
(83, 'MTHFR (A1298C)*' , '' , 18, 2, 'wt/wt,mut/wt,mut/mut'),
(84, 'MTHFR (C677T)*' , '' , 18, 3, 'wt/wt,mut/wt,mut/mut'),
(85, 'Factor II (G20210A)*' , '' , 18, 4, 'wt/wt,mut/wt,mut/mut'),
(86, 'Factor V R2 (H1299R)*' , '' , 18, 5, 'wt/wt,mut/wt,mut/mut'),
(87, 'PAI-1 (4G/5G)*' , '' , 18, 6, '5G/5G,4G/5G,4G/4G'),
(88, 'Factor XIII (V34L)*' , '' , 18, 7, 'wt/wt,mut/wt,mut/mut'),
(89, 'EPCR (A4600G)*' , '' , 18, 8, 'A/A,G/A,G/G'),
(90, 'EPCR (G4678C)*' , '' , 18, 9, 'G/G,C/G,C/C'),
(91, 'GpIa (C807T)' , '---' , 18, 10, ''),
(92, 'GpIIIa (T1565C)' , '---' , 18, 11, ''),

(93, 'Factor V Leiden (G1691A)*' , '' , 19, 1, 'wt/wt,mut/wt,mut/mut'),
(94, 'MTHFR (A1298C)*' , '' , 19, 2, 'wt/wt,mut/wt,mut/mut'),
(95, 'MTHFR (C677T)*' , '' , 19, 3, 'wt/wt,mut/wt,mut/mut'),
(96, 'Factor II (G20210A)*' , '' , 19, 4, 'wt/wt,mut/wt,mut/mut'),
(97, 'Factor V R2 (H1299R)*' , '' , 19, 5, 'wt/wt,mut/wt,mut/mut'),
(98, 'PAI-1 (4G/5G)*' , '' , 19, 6, '5G/5G,4G/5G,4G/4G'),
(99, 'Factor XIII (V34L)*' , '' , 19, 7, 'wt/wt,mut/wt,mut/mut'),
(100, 'EPCR (A4600G)*' , '' , 19, 8, 'A/A,G/A,G/G'),
(101, 'EPCR (G4678C)*' , '' , 19, 9, 'G/G,C/G,C/C'),
(102, 'GpIa (C807T)' , '' , 19, 10, ''),
(103, 'GpIIIa (T1565C)' , '' , 19, 11, ''),

(104, 'R702W' , '' , 21, 1, ''),
(105, 'G908R' , '' , 21, 2, ''),
(106, '3020 ins C' , '' , 21, 3, ''),

(107, 'EGFR exon 19<sup>*G</sup>', '', 22, 1, ''),
(108, 'EGFR exon 21<sup>*G</sup>', '', 22, 2, ''),
(109, 'KRAS (exon 2)<sup>*G</sup>', '', 23, 3, ''),
(110, 'KRAS (exon 3)', '', 23, 4, ''),
(111, 'KRAS (exon 4)', '', 23, 5, ''),

(112, 'V617F' , '' , 24, 1, ''),

(113, 'Factor V Leiden (G1691A)*' , '' , 57, 1, 'wt/wt,mut/wt,mut/mut'),
(114, 'Factor II (G20210A)*' , '' , 57, 2, 'wt/wt,mut/wt,mut/mut'),

(115, 'NRAS (exon 2)' , '' , 58, 1, ''),
(116, 'NRAS (exon 3)' , '' , 58, 2, ''),
(117, 'NRAS (exon 4)' , '' , 58, 3, ''),
(118, 'M (major) BCR' , '' , 59, 1, ''),
(119, 'm (minor) BCR' , '' , 59, 2, ''),
(120, 'µ (micro) BCR' , '' , 59, 3, ''),
(121, 'KRAS (exon 2)<sup>*G</sup>', '', 60, 1, '');


update appmodul set cdokladu=null, formneprovvys=null, provadianalyzu =null, typymaterialu =null, uvolnujianalyzu =null, vedoucilekari =null where nazev = 'Ostrava';
update appmodul set provadianalyzu = 'RNDr. Markéta Šaňková, Ph.D.;Mgr. Tomáš Pexa; Lenka Konečná; Dana Olejníčková; Dana Olejníčková, Lenka Konečná'  where nazev = 'Brno';