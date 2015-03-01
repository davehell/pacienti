update appmodul set adresa=STRINGDECODE('Opavsk\u00e1 962/39, 708 00 Ostrava Poruba, tel.: +420 597 437 520') where id = 1;

-- update examination set certif=true, popis=STRINGDECODE('Stanoven\u00ed predispozice k ateroskler\u00f3ze metodou PCR + reverzn\u00ed hybridizace, SOP OV \u010d. 13 *') where id=2;
update examination set certif=true, popis=STRINGDECODE('Stanoven\u00ed inzerce A(TA)7 TAA v promotoru genu UGT1A1 metodou PCR + fragmenta\u010dn\u00ed anal\u00fdzy - Gilbert\u016fv syndrom, SOP OV \u010d. 8 *') where id=14;

update genotype set autocompl='a/a,a/b,b/b' where id=5;

delete from score where vysetreni_id=23;
delete from score where vysetreni_id=58;
delete from score where vysetreni_id=60;

INSERT INTO SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, KOD, POPIS, POCET, VYKON_ID, VYSETRENI_ID) VALUES
(98, 0, FALSE, FALSE, NULL, NULL, 8, 2, 23),
(99, 0, FALSE, FALSE, NULL, NULL, 1, 3, 23),
(100, 0, FALSE, FALSE, NULL, NULL, 8, 2, 23),
(101, 0, FALSE, FALSE, NULL, NULL, 8, 7, 23),

(102, 0, FALSE, FALSE, NULL, NULL, 8, 2, 58),
(103, 0, FALSE, FALSE, NULL, NULL, 1, 3, 58),
(104, 0, FALSE, FALSE, NULL, NULL, 8, 2, 58),
(105, 0, FALSE, FALSE, NULL, NULL, 8, 7, 58),

(106, 0, FALSE, FALSE, NULL, NULL, 8, 2, 60),
(107, 0, FALSE, FALSE, NULL, NULL, 1, 3, 60),
(108, 0, FALSE, FALSE, NULL, NULL, 8, 2, 60),
(109, 0, FALSE, FALSE, NULL, NULL, 8, 7, 60);
