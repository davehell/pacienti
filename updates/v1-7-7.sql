delete FROM USER where modul_id > 2;
delete FROM INSURANCECOMPANY where modul_id > 2;
delete FROM APPMODUL where id > 2;

--id 96
update operation set jednoudenne=true where id=4;
--id 101
update examination set popis=STRINGDECODE('Stanoven\u00ed trombofiln\u00edch mutac\u00ed metodou real-time PCR (SOPB \u010d. 1)') where id>=17 and id<=19;
--id 102
update genotype set nazev='Factor V Leiden (G1691A)*' where id=93;
update genotype set nazev='MTHFR (A1298C)*' where id=94;
update genotype set nazev='Factor V R2 (H1299R)*' where id=95;
update genotype set nazev='Factor XIII (V34L)*' where id=96;
update genotype set nazev='Factor II (G20210A)*' where id=97;
update genotype set nazev='PAI-1 (4G/5G)*' where id=98;
update genotype set nazev='MTHFR (C677T)*' where id=99;
update genotype set nazev='EPCR (A4600G)*' where id=100;
update genotype set nazev='EPCR (G4678C)*' where id=101;
update genotype set nazev='Factor V Leiden (G1691A)*' where id=104;
update genotype set nazev='MTHFR (A1298C)*' where id=105;
update genotype set nazev='Factor V R2 (H1299R)*' where id=106;
update genotype set nazev='Factor XIII (V34L)*' where id=107;
update genotype set nazev='Factor II (G20210A)*' where id=108;
update genotype set nazev='PAI-1 (4G/5G)*' where id=109;
update genotype set nazev='MTHFR (C677T)*' where id=110;
update genotype set nazev='EPCR (A4600G)*' where id=111;
update genotype set nazev='EPCR (G4678C)*' where id=112;
update genotype set nazev='Factor V Leiden (G1691A)*' where id=115;
update genotype set nazev='MTHFR (A1298C)*' where id=116;
update genotype set nazev='Factor V R2 (H1299R)*' where id=117;
update genotype set nazev='Factor XIII (V34L)*' where id=118;
update genotype set nazev='Factor II (G20210A)*' where id=119;
update genotype set nazev='PAI-1 (4G/5G)*' where id=120;
update genotype set nazev='MTHFR (C677T)*' where id=121;
update genotype set nazev='EPCR (A4600G)*' where id=122;
update genotype set nazev='EPCR (G4678C)*' where id=123;
--id 103
update examination set popis=STRINGDECODE('Stanoven\u00ed mutac\u00ed v CFTR genu metodou reverzn\u00ed hybridizace<br>(SOPB \u010d. 2) *') where id=7;
--id 105
update examination set popis=STRINGDECODE('Stanoven\u00ed mutac\u00ed v k\u00f3duj\u00edc\u00ed sekvenci genu pro Connexin 26 a intronov\u00e9 mutace c.IVS 1+1 G>A - prelingu\u00e1ln\u00ed hluchota, SOP OV \u010d. 9') where id=12;
update examination set popis=STRINGDECODE('Stanoven\u00ed vybran\u00fdch alel HLA II. t\u0159\u00eddy asociovan\u00fdch s rozvojem celiak\u00e1ln\u00ed sprue metodou PCR a detekc\u00ed na agar\u00f3zov\u00e9m gelu (DQA1 a DQB1), SOP OV \u010d. 3 *') where id=15;
update examination set popis=STRINGDECODE('Stanoven\u00ed exprese antigenu HLA B 27 asociovan\u00e9ho s revmatick\u00fdmi chorobami - Becht\u011brevova nemoc, SOP OV \u010d. 6') where id=13;
update examination set popis=STRINGDECODE('Stanoven\u00ed mutace V617F v genu JAK-2 - polycytemia vera, SOP OV \u010d.12') where id=24;
