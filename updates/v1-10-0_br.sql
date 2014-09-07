ALTER TABLE user ADD uvolnuje varchar(100) default '';

update user set username='jf' where id=3;
update user set username='lh' where id=4;
update user set username='hk' where id=5;
update user set PASSWORDHASH='83ff63f5ff3518a2cba17bde7238d3087b5a964c' where id=1;

INSERT INTO USER(ID, ISADMIN, ISDOCTOR, UVOLNUJE, JMENO, PARAFA, PASSWORDHASH, POZICE, USERNAME, MODUL_ID) VALUES
(6,  FALSE, TRUE, '', STRINGDECODE('MUDr. Ditta Leznarov\u00e1'), 'DL', 'c80025c509da3ae020a269316c8278405c463d76', STRINGDECODE(', vedouc\u00ed l\u00e9ka\u0159'), 'Leznarova', 2),
(12, FALSE, TRUE, '', STRINGDECODE('MUDr. Jana Zvolsk\u00e1'), 'JZ', 'd1756f07ee8acde98127278196953f961d16bda0', STRINGDECODE(', vedouc\u00ed l\u00e9ka\u0159'), 'Zvolska', 1);

update user set uvolnuje=STRINGDECODE(', vedouc\u00ed pracovi\u0161t\u011b') where id=5;
update user set uvolnuje=STRINGDECODE(', vedouc\u00ed laborato\u0159e') where id=9;
update user set uvolnuje=STRINGDECODE(', vedouc\u00ed l\u00e9ka\u0159') where id=6;
update user set uvolnuje=STRINGDECODE(', vedouc\u00ed l\u00e9ka\u0159') where id=12;

update appmodul set
CDOKLADU = '5',
FORMNEPROVVYS ='IF-71.0',
KOD = 'KO',
NAZEV = 'Ostrava',
PROVADIANALYZU = STRINGDECODE('RNDr. Mark\u00e9ta \u0160a\u0148kov\u00e1, Ph.D.;Mgr. Tom\u00e1\u0161 Pexa; Lenka Kone\u010dn\u00e1; Dana Olejn\u00ed\u010dkov\u00e1; Dana Olejn\u00ed\u010dkov\u00e1, Lenka Kone\u010dn\u00e1'),
TYPYMATERIALU = STRINGDECODE('vzorek tk\u00e1n\u011b-histologick\u00fd blo\u010dek;perifern\u00ed krev;izolovan\u00e1 DNA;FTA karta krev;FTA karta plodov\u00e1 voda;vzorek tk\u00e1n\u011b-skl\u00ed\u010dko'),
UVOLNUJIANALYZU = STRINGDECODE('RNDr. Hana Kola\u0159\u00edkov\u00e1, vedouc\u00ed pracovi\u0161t\u011b;MUDr. Jana Zvolsk\u00e1, vedouc\u00ed l\u00e9ka\u0159;RNDr. Mark\u00e9ta Zachov\u00e1, Ph.D., vedouc\u00ed laborato\u0159e;MUDr. Ditta Leznarov\u00e1, vedouc\u00ed l\u00e9ka\u0159'),
VEDOUCILEKARI = STRINGDECODE('RNDr. Ji\u0159\u00ed Fi\u0161er;Mgr. Lenka Hrd\u00e1;RNDr. Hana Kola\u0159\u00edkov\u00e1;RNDr. Lucie Bene\u0161ov\u00e1, Ph.D.;Bc. Barbora Bel\u0161\u00e1nov\u00e1;RNDr. Lucie Bene\u0161ov\u00e1, Ph.D. / Bc. Barbora Bel\u0161\u00e1nov\u00e1'),
ADRESA = STRINGDECODE('Opavsk\u00e1 962/39, 708 00 Ostrava Poruba, tel.: +420 596 973 356')
WHERE id = 1;
