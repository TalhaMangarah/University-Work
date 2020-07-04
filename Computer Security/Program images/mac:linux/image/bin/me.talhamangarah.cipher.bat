@echo off
set DIR="%~dp0"
set JAVA_EXEC="%DIR:"=%\java"
pushd %DIR% & %JAVA_EXEC%  -p "%~dp0/../app" -m me.talhamangarah.cipher/me.talhamangarah.cipher.Main  %* & popd
