-- pacienti-1-7-4.sql
-- RUNSCRIPT FROM 'D:/pacienti-1-7-4.sql'

update biomaterial set datumizolace='2012-12-31' where datumprijeti<'2012-12-31' and datumizolace is null;

SET REFERENTIAL_INTEGRITY FALSE;

truncate table examination;
INSERT INTO examination   SELECT * FROM CSVREAD('D:/ok-examination.csv', null, 'charset=UTF-8');

truncate table score;
INSERT INTO score SELECT * FROM CSVREAD('D:/ok-score.csv', null, 'charset=UTF-8');

truncate table genotype;
INSERT INTO genotype SELECT * FROM CSVREAD('D:/ok-genotype.csv', null, 'charset=UTF-8');

-- truncate table user;
-- INSERT INTO user SELECT * FROM CSVREAD('D:/ok-user.csv', null, 'charset=UTF-8');

SET REFERENTIAL_INTEGRITY TRUE;
