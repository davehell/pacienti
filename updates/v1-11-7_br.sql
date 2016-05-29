INSERT INTO PUBLIC.EXAMINATION(ID, AKTUAL, BODY, CERTIF, NAZEV, POPIS, SLOUPCE, VYSVETLIVKA, ZKRATKA, GENOMAC, TAT) VALUES
(65, TRUE, 0, FALSE, 'Thiopurin', STRINGDECODE('Stanoven\u00ed d\u011bdi\u010dn\u011b podm\u00edn\u011bn\u00e9 odpov\u011bdi organizmu na l\u00e9\u010dbu thiopurinov\u00fdmi l\u00e9\u010divy metodou reverzn\u00ed hybridizace (metodika \u010d. 12)'), 1, STRINGDECODE('((wt)) \u2013 &bdquo;wild type&rdquo; = &bdquo;ne-mutantn\u00ed&rdquo; alela, ((mut)) \u2013 &bdquo;mutantn\u00ed&rdquo; alela'), 'TPMT', FALSE, 10),
(66, TRUE, 0, FALSE, 'Papilomaviry (HPV)', STRINGDECODE('Detekce vysoce rizikov\u00fdch lidsk\u00fdch papilomavir\u016f (HPV) metodou real time PCR (metodika \u010d. 30)'), 1, '', 'HPV', FALSE, 10);

UPDATE PUBLIC.EXAMINATION SET AKTUAL = FALSE WHERE id = 64;

INSERT INTO PUBLIC.GENOTYPE(ID, NAZEV, VYCHOZI, VYSETRENI_ID, AUTOCOMPL, PORADI) VALUES
(239, 'G238C', '', 65, 'wt/wt,mut/wt,mut/mut', 1),
(240, 'G460A', '', 65, 'wt/wt,mut/wt,mut/mut', 2),
(241, 'A719G', '', 65, 'wt/wt,mut/wt,mut/mut', 3),
(242, 'HPV 16', '', 66, '', 1),
(243, 'HPV 18', '', 66, '', 2),
(244, STRINGDECODE('HPV ostatn\u00ed'), '', 66, '', 3);