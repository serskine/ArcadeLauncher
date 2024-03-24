echo off
Title ArcadeLauncher - No Arguments

set "LIB_DIR=\lib"
set "NATIVE_DIR=%LIB_DIR%\native"
set "JAR_FILE=%LIB_DIR%\jinput-2.0.9.jar"
set "APP_JAR=ArcadeLauncher.jar"
set "MAIN_CLASS=launcher.ArcadeLauncher"

java -Djava.library.path="%NATIVE_DIR%" -cp "%JAR_FILE%;%APP_JAR%" %MAIN_CLASS%

pause