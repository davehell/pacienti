ALTER TABLE examination ADD tat int default 0;
update examination set tat=10 where aktual=true;

ALTER TABLE patient ADD neuplnazadanka boolean default 0;
ALTER TABLE biomaterial ADD nevyhovujicivzorek boolean default 0;
ALTER TABLE report ADD opakovanevysetreni boolean default 0;
