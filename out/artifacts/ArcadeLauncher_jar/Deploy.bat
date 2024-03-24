@echo off
title Deploying ArcadeLauncher artifacts
cls

setlocal enabledelayedexpansion

set "source_dir=C:\Users\soupt\IdeaProjects\ArcadeLauncher\out\artifacts\ArcadeLauncher_jar"
rem List of files to copy
set "files_to_copy=ArcadeLauncher.jar ArcadeLauncher.bat"
rem List of destinations
set "destinations=S:\Public\Downloads P:\Public\Downloads"

echo Deploying ArcadeLauncher Artifacts - Started

rem Change to the source directory
echo Changing to working directory "%source_dir%"
cd /d "%source_dir%" || (
    echo Failed to change directory to "%source_dir%"
    exit /b 1
)

rem Iterate over each destination in the list

set "copy_failure=false"
for %%d in (%destinations%) do (
    echo Copying to "%%d" Machine

    rem Iterate over each file to copy
    for %%f in (%files_to_copy%) do (
        rem Construct source and destination paths
        set "source_file=%source_dir%\%%f"
        set "destination_file=%%d\%%f"

        rem Copy the file
        copy "!source_file!" "!destination_file!" > nul && set "copy_result=success" || (
            set "copy_result=failure"
            set "copy_failure=true"
        )

		rem Output the result based on the copy_result variable
        echo  - Deployement is a !copy_result! for "!destination_file!" 
    )
)

echo Deploying ArcadeLauncher Artifacts - Complete

rem Minimum delay of 2 seconds
timeout /t 2 /nobreak >nul

if "%copy_failure%"=="true" (
    pause
)
