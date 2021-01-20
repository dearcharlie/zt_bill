@echo off
set BASEDIR=%~dp0
set webdriver.chrome.driver=%BASEDIR%chromedriver.exe
set INPUT=%BASEDIR%bill_input.xlsx
set OUTPUT=%BASEDIR%bill_output.xlsx

java -jar %BASEDIR%user-tools-0.0.1.jar --input=%INPUT% --output=%OUTPUT%

@pause

