@echo off
echo Zjisteni IP adresy pocitace
for /f "usebackq tokens=2 delims=:" %%f in (`ipconfig ^| findstr /c:"Adresa IPv4"`) do echo IP adresa je: %%f
pause