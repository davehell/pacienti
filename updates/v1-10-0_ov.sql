ALTER TABLE user ADD uvolnuje varchar(100) default '';

delete from user where modul_id = 2;
delete from user where id = 1;
delete from user where id = 8;
INSERT INTO PUBLIC.USER(ID, ISADMIN, ISDOCTOR, JMENO, PARAFA, PASSWORDHASH, POZICE, USERNAME, MODUL_ID, UVOLNUJE) VALUES
(1, TRUE, TRUE, 'servis Ostrava', 'ostrava', '83ff63f5ff3518a2cba17bde7238d3087b5a964c', 'servis Ostrava', 'Ostrava', 1, ''),
(2, TRUE, FALSE, 'servis Brno', 'brno', '27d51cbc674f7536e76aca7f7038781ebbe2a91b', 'servis Brno', 'Brno', 2, ''),
(8, FALSE, TRUE, STRINGDECODE('Mgr. Tom\u00e1\u0161 Pexa'), 'TP', '2d05f07600eea984f104c7803b417e03fb89c08e', STRINGDECODE('odborn\u00fd pracovn\u00edk'), 'Pexa', 2, ''),
(9, FALSE, TRUE, STRINGDECODE('RNDr. Mark\u00e9ta \u0160a\u0148kov\u00e1, Ph.D.'), STRINGDECODE('M\u0160'), 'ed220dc8b6be1aad42732550a081bc8ef01dde98', STRINGDECODE('vedouc\u00ed laborato\u0159e'), 'Zachova', 2, STRINGDECODE('vedouc\u00ed laborato\u0159e')),
(10, FALSE, TRUE, STRINGDECODE('Kone\u010dn\u00e1'), 'LK', '6e5b8cf13ec8938300abe7d4844cc735d14fd2ed', STRINGDECODE('odborn\u00fd pracovn\u00edk'), 'Konecna', 2, ''),
(11, FALSE, TRUE, STRINGDECODE('Olejn\u00ed\u010dkov\u00e1'), 'DO', '9d6f39d94c5b4420ebf1a1c284529e032693cc2a', STRINGDECODE('odborn\u00fd pracovn\u00edk'), 'Olejnickova', 2, ''),
(6,  FALSE, TRUE, '', STRINGDECODE('MUDr. Ditta Leznarov\u00e1'), 'DL', 'c80025c509da3ae020a269316c8278405c463d76', STRINGDECODE(', vedouc\u00ed l\u00e9ka\u0159'), 'Leznarova', 2),
(12, FALSE, TRUE, '', STRINGDECODE('MUDr. Jana Zvolsk\u00e1'), 'JZ', 'd1756f07ee8acde98127278196953f961d16bda0', STRINGDECODE(', vedouc\u00ed l\u00e9ka\u0159'), 'Zvolska', 1);

update user set uvolnuje=STRINGDECODE(', vedouc\u00ed pracovi\u0161t\u011b') where id=5;
update user set uvolnuje=STRINGDECODE(', vedouc\u00ed laborato\u0159e') where id=9;

update appmodul set
CDOKLADU = '',
FORMNEPROVVYS ='',
KOD = 'K',
NAZEV = 'Brno',
PROVADIANALYZU = STRINGDECODE('RNDr. Mark\u00e9ta \u0160a\u0148kov\u00e1, Ph.D.;Mgr. Tom\u00e1\u0161 Pexa; Lenka Kone\u010dn\u00e1; Dana Olejn\u00ed\u010dkov\u00e1; Dana Olejn\u00ed\u010dkov\u00e1, Lenka Kone\u010dn\u00e1'),
TYPYMATERIALU = STRINGDECODE('krev nesr\u00e1\u017eliv\u00e1 EDTA;izolovan\u00e1 DNA;FTA karta krev;FTA karta plodov\u00e1 voda'),
UVOLNUJIANALYZU = STRINGDECODE('RNDr. Mark\u00e9ta \u0160a\u0148kov\u00e1, Ph.D., vedouc\u00ed laborato\u0159e;MUDr. Ditta Leznarov\u00e1'),
VEDOUCILEKARI = STRINGDECODE('MUDr. Ditta Leznarov\u00e1'),
ADRESA = STRINGDECODE('Tvrd\u00e9ho 2a, 602 00 Brno, tel.: +420 543 185 811')
WHERE id = 2;
