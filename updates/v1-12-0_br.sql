ALTER TABLE examination ADD VOLITGENOTYPY boolean;

delete from genotype where id = 83;
delete from genotype where id = 84;
delete from genotype where id = 86;
delete from genotype where id = 88;
delete from genotype where id = 89;
delete from genotype where id = 90;
delete from genotype where id = 93;
delete from genotype where id = 96;

update examination set VOLITGENOTYPY = false;
update examination set VOLITGENOTYPY = true where id = 18;
update examination set VOLITGENOTYPY = true where id = 19;

INSERT INTO PUBLIC.OPERATION(ID, JEDNOUDENNE, JEDNOUNAVZOREK, KOD, POPIS) VALUES
(10, TRUE, FALSE, '94973', STRINGDECODE('Vy\u0161et\u0159en\u00ed 2 trombofiln\u00edch mutac\u00ed')),
(11, TRUE, FALSE, '94974', STRINGDECODE('Vy\u0161et\u0159en\u00ed 3 trombofiln\u00edch mutac\u00ed')),
(12, TRUE, FALSE, '94973', STRINGDECODE('Vy\u0161et\u0159en\u00ed 5 trombofiln\u00edch mutac\u00ed'));

update score set body = 5796, pocet = 1, vykon_id = 10 where id = 2;
update score set body = 8060, pocet = 1, vykon_id = 11 where id = 4;
update score set body = 10324, pocet = 1, vykon_id = 12 where id = 6;
update score set body = 5796, pocet = 1, vykon_id = 10 where id = 97;



