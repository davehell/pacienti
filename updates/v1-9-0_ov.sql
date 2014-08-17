ALTER TABLE examination ADD tat int default 0;
update examination set tat=10 where aktual=true;

update examination set tat=20 where id=4;
update examination set tat=20 where id=12;
update examination set tat=20 where id=13;
update examination set tat=20 where id=14;
update examination set tat=20 where id=21;
update examination set tat=20 where id=24;

update user  set passwordhash='112f489f6b265cdf4e4892f7b952cd36fc0ecd5f' where username='jm';

ALTER TABLE patient ADD neuplna_zadanka boolean default 0;
ALTER TABLE biomaterial ADD nevyhovujici_vzorek boolean default 0;
ALTER TABLE report ADD opakovane_vysetreni boolean default 0;