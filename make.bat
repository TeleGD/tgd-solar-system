@echo off
setlocal

if "%1"=="" (
    echo Autostart
    GOTO autostart
) else (
    echo Parsing
    GOTO loop
)

:autostart
if not exist "bin" (
    echo Build
    mkdir "bin"
    javac -d bin -cp src;res;lib\common\*;lib\x86\* src\Main.java
)
goto RUN

:loop
if x%1 equ x goto done
goto checkParam

:paramError
echo %1 n'est pas une option valide
echo Usage: make.bat [build^|run^|clean...]
goto next

:next
shift /1
goto loop

:checkParam
if "%1" equ "build" goto BUILD
if "%1" equ "clean" goto CLEAN
if "%1" equ "run" goto RUN
goto paramError

:BUILD
    echo Build
    if not exist "bin" mkdir "bin"
    javac -d bin -cp src;res;lib\common\*;lib\x86\* src\Main.java
    GOTO next

:CLEAN
    echo Clean
    rmdir /Q /S bin
    GOTO next

:RUN
    echo Run
    java -Djava.library.path=sys\x86 -cp bin;res;lib\common\*;lib\x86\* Main
    GOTO next

:done
