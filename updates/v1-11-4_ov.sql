INSERT INTO PUBLIC.USER(ID, ISADMIN, JMENO, PARAFA, PASSWORDHASH, POZICE, USERNAME, MODUL_ID, ISDOCTOR, UVOLNUJE) VALUES
(40, FALSE, STRINGDECODE('Mgr. Ren\u00e1ta Novotn\u00e1'), 'RN', 'aaa', 'laborantka', 'rn', 1, TRUE, '');

update PUBLIC.APPMODUL set PROVADIANALYZU=STRINGDECODE('RNDr. Ji\u0159\u00ed Fi\u0161er;Mgr. Lenka Hrd\u00e1;RNDr. Hana Kola\u0159\u00edkov\u00e1;RNDr. Lucie Bene\u0161ov\u00e1, Ph.D.;Mgr. Ren\u00e1ta Novotn\u00e1;RNDr. Lucie Bene\u0161ov\u00e1, Ph.D. / Bc. Barbora Bel\u0161\u00e1nov\u00e1;Mgr. Ren\u00e1ta Novotn\u00e1') where id = 1;

-- vysetreni CALR
update PUBLIC.EXAMINATION set TAT = 20	 where id = 62;
