update examination set zkratka = 'TC3' where id = 19;
  
update operation set kod = '94975' where id = 12;

delete from operation where id = 13;
INSERT INTO PUBLIC.OPERATION(ID, JEDNOUDENNE, JEDNOUNAVZOREK, KOD, POPIS) VALUES
(13, FALSE, FALSE, '94977', STRINGDECODE('Vy\u0161et\u0159en\u00ed cystick\u00e9 fibr\u00f3zy'));        

delete from score where vysetreni_id = 5;
delete from score where vysetreni_id = 3;
delete from score where vysetreni_id = 61;
delete from score where vysetreni_id = 8;
delete from score where vysetreni_id = 63;
delete from score where vysetreni_id = 19;
delete from score where vysetreni_id = 18;
delete from score where vysetreni_id = 17;


INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, VYSETRENI_ID, VYKON_ID, POCET) VALUES
(114, 0, FALSE, FALSE, 3, 1, 1),
(115, 0, FALSE, FALSE, 3, 2, 4),
(116, 0, FALSE, FALSE, 3, 3, 1),
(117, 0, FALSE, FALSE, 3, 4, 1),
(118, 0, FALSE, FALSE, 3, 5, 2);

INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, VYSETRENI_ID, VYKON_ID, POCET) VALUES
(119, 0, FALSE, FALSE, 5, 1, 1),
(120, 0, FALSE, FALSE, 5, 2, 4),
(121, 0, FALSE, FALSE, 5, 3, 1),
(122, 0, FALSE, FALSE, 5, 4, 1),
(123, 0, FALSE, FALSE, 5, 5, 2);

INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, VYSETRENI_ID, VYKON_ID, POCET) VALUES
(124, 0, FALSE, FALSE, 8, 1, 1),
(125, 0, FALSE, FALSE, 8, 6, 4);

INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, VYSETRENI_ID, VYKON_ID, POCET) VALUES
(126, 0, FALSE, FALSE, 17, 1, 1),
(127, 0, FALSE, FALSE, 17, 10, 1);
INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, VYSETRENI_ID, VYKON_ID, POCET) VALUES
(128, 0, FALSE, FALSE, 18, 1, 1),
(129, 0, FALSE, FALSE, 18, 11, 1);
INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, VYSETRENI_ID, VYKON_ID, POCET) VALUES
(130, 0, FALSE, FALSE, 19, 1, 1),
(131, 0, FALSE, FALSE, 19, 12, 1);

INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, VYSETRENI_ID, VYKON_ID, POCET) VALUES
(132, 0, FALSE, FALSE, 61, 1, 1),
(133, 0, FALSE, FALSE, 61, 9, 3),
(134, 0, FALSE, FALSE, 61, 2, 3),
(135, 0, FALSE, FALSE, 61, 7, 3);

INSERT INTO PUBLIC.SCORE(ID, BODY, JEDNOUDENNE, JEDNOUNAVZOREK, VYSETRENI_ID, VYKON_ID, POCET) VALUES
(136, 0, FALSE, FALSE, 63, 1, 1),
(137, 0, FALSE, FALSE, 63, 13, 1);