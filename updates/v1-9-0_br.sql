ALTER TABLE examination ADD tat int default 0;
update examination set tat=10 where aktual=true;

ALTER TABLE patient ADD neuplna_zadanka boolean default 0;
ALTER TABLE biomaterial ADD nevyhovujici_vzorek boolean default 0;
ALTER TABLE report ADD opakovane_vysetreni boolean default 0;