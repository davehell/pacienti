-- 56: izolace dna
-- 	- 1: 94119	1x
-- 8: AZF
-- 	- 6: 94123	4x
-- 61: SMA
-- 	- 9: 94189     3x
-- 	- 2: 94199     3x
-- 	- 7: 94127     3x
-- 63: CF2
-- 	- 6: 94123     6x
-- 	- 7: 94127     6x

delete from score where id = 56;
delete from score where id = 8;
delete from score where id = 61;
delete from score where id = 63;

INSERT INTO score(ID, pocet, vykon_id, vysetreni_id, BODY, JEDNOUDENNE, JEDNOUNAVZOREK) VALUES (113, 1, 1, 56, 0, FALSE, FALSE);

INSERT INTO score(ID, pocet, vykon_id, vysetreni_id, BODY, JEDNOUDENNE, JEDNOUNAVZOREK) VALUES (114, 4, 6, 8, 0, FALSE, FALSE);

update operation set jednoudenne = true where id = 9;
INSERT INTO score(ID, pocet, vykon_id, vysetreni_id, BODY, JEDNOUDENNE, JEDNOUNAVZOREK) VALUES (115, 3, 9, 61, 0, FALSE, FALSE);
INSERT INTO score(ID, pocet, vykon_id, vysetreni_id, BODY, JEDNOUDENNE, JEDNOUNAVZOREK) VALUES (116, 3, 2, 61, 0, FALSE, FALSE);
INSERT INTO score(ID, pocet, vykon_id, vysetreni_id, BODY, JEDNOUDENNE, JEDNOUNAVZOREK) VALUES (117, 3, 7, 61, 0, FALSE, FALSE);

INSERT INTO score(ID, pocet, vykon_id, vysetreni_id, BODY, JEDNOUDENNE, JEDNOUNAVZOREK) VALUES (118, 6, 6, 63, 0, FALSE, FALSE);
INSERT INTO score(ID, pocet, vykon_id, vysetreni_id, BODY, JEDNOUDENNE, JEDNOUNAVZOREK) VALUES (119, 6, 7, 63, 0, FALSE, FALSE);
