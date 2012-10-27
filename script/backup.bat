set myDate=%date:~9,4%-%date:~6,2%-%date:~3,2%
set myDate=%myDate: =0%
copy /Y "C:\pacienti\db\h2\play.h2.db" "C:\GenMedFor DB\zaloha\LFG\DATABAZE PACIENTU\%myDate%_play.h2.db"
