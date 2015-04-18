update appmodul set provadianalyzu=STRINGDECODE('RNDr. Ji\u0159\u00ed Fi\u0161er;Mgr. Lenka Hrd\u00e1;RNDr. Hana Kola\u0159\u00edkov\u00e1;RNDr. Lucie Bene\u0161ov\u00e1, Ph.D.;Bc. Barbora Bel\u0161\u00e1nov\u00e1;RNDr. Lucie Bene\u0161ov\u00e1, Ph.D. / Bc. Barbora Bel\u0161\u00e1nov\u00e1;Bc. Kl\u00e1ra Banotov\u00e1') where id=1;

update score set vykon_id=1, pocet=1 where id=106;
update score set vykon_id=2, pocet=5 where id=107;
update score set vykon_id=3, pocet=1 where id=108;
update score set vykon_id=7, pocet=4 where id=109;

update score set vykon_id=1, pocet=1 where id=98;
update score set vykon_id=2, pocet=21 where id=99;
update score set vykon_id=3, pocet=2 where id=100;
update score set vykon_id=7, pocet=12 where id=101;

ALTER TABLE biomaterial ADD DATUMIZOLACERNA timestamp;
ALTER TABLE patient ADD KONCRNA VARCHAR(255);