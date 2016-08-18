update EXAMINATION set VYSVETLIVKA = STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela') where id = 62;
update EXAMINATION set POPIS = STRINGDECODE('Stanoven\u00ed mutace V617F v exonu 10 a mutac\u00ed v exonu 12 genu JAK2 metodou Real Time PCR + sekvena\u010dn\u00ed PCR, SOP OV \u010d. 12') where id = 24;
update EXAMINATION set POPIS = STRINGDECODE('Stanoven\u00ed f\u00fazn\u00edho genu BCR-ABL (Ph1 chromozom): p\u0159estavby M bcr, m bcr a \u00b5 bcr metodou RT PCR,<br>SOP OV \u010d. 14') where id = 59;
update EXAMINATION set ZKRATKA = 'BCR-ABL' where id = 59;
update EXAMINATION set POPIS = STRINGDECODE('Stanoven\u00ed mutac\u00ed  R702W, G908R a 3020 ins C v genu NOD 2/CARD 15 metodou Real Time PCR - Crohnova choroba, SOP OV \u010d. 11') where id = 21;
update EXAMINATION set POPIS = STRINGDECODE('Stanoven\u00ed mutac\u00ed v k\u00f3duj\u00edc\u00ed sekvenci genu pro Connexin 26 a intronov\u00e9 mutace c.IVS 1+1 G>A metodou sekvena\u010dn\u00ed PCR   - prelingu\u00e1ln\u00ed hluchota, SOP OV \u010d. 9') where id = 12;
update EXAMINATION set POPIS = STRINGDECODE('Stanoven\u00ed mutac\u00ed v exonech 8, 14, 15 genu ATP7B metodou sekvena\u010dn\u00ed PCR - Morbus Wilson, SOP OV \u010d. 10') where id = 4;
update EXAMINATION set POPIS = STRINGDECODE('Molekul\u00e1rn\u00ed genetick\u00e9 vy\u0161et\u0159en\u00ed antigenu HLA B 27 asociovan\u00e9ho s revmatick\u00fdmi chorobami metodou PCR  a detekc\u00ed na agar\u00f3zov\u00e9m gelu - Becht\u011brevova nemoc, SOP OV \u010d. 6') where id = 13;
update EXAMINATION set POPIS = STRINGDECODE('Stanoven\u00ed mutac\u00ed C807T v genu GpIa a L33P v genu GpIIIa metodou Real Time PCR, SOP OV \u010d. 1') where id = 16;
update EXAMINATION set POPIS = STRINGDECODE('Stanoven\u00ed mutac\u00ed -13910C/T a -22018G/A v genu LCT metodou Real Time PCR  - lakt\u00f3zov\u00e1 intolerance, SOP OV \u010d.7') where id = 11;


INSERT INTO PUBLIC.EXAMINATION(ID, AKTUAL, BODY, CERTIF, NAZEV, POPIS, SLOUPCE, VYSVETLIVKA, ZKRATKA, GENOMAC, TAT, VOLITGENOTYPY) VALUES (68, TRUE, 0, FALSE, 'MPL', STRINGDECODE('Stanoven\u00ed mutac\u00ed v exonu 10 genu MPL metodou sekvena\u010dn\u00ed PCR, SOP OV \u010d. 17'), 1, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela'), 'MPL', FALSE, 20, FALSE);         

delete from genotype where id = 112;
INSERT INTO PUBLIC.GENOTYPE(ID, NAZEV, VYCHOZI, VYSETRENI_ID, AUTOCOMPL, PORADI) VALUES
(249, 'JAK2, exon 10, V617F', '', 24, '', 1),
(250, 'JAK2, exon 12', '', 24, '', 2),
(251, 'MPL, exon 10', '', 68, '', 1);

update EXAMINATION set VYSVETLIVKA = STRINGDECODE('negativn\u00ed = p\u0159estavba nenalezena, pozitivn\u00ed = p\u0159estavba nalezena') where id = 59;
update EXAMINATION set CERTIF = TRUE where id = 59;
update EXAMINATION set sloupce = 1 where id = 67;
INSERT INTO PUBLIC.GENOTYPE(ID, NAZEV, VYCHOZI, VYSETRENI_ID, AUTOCOMPL, PORADI) VALUES
(252, 'MPL, exon 10, W515L', '', 68, '', 2),
(253, STRINGDECODE('N\u00e1lez'), '', 67, '', 1);
update genotype set NAZEV = 'MPL, exon 10, W515K' where id = 251;
