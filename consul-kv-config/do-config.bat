@echo off 
setlocal enableextensions 

rem Remove all keys to have a clean slate
consul kv delete -recurse

rem Add keys as YAML data from each file in this folder
for /r %%f in (*) do (
	set /P text=| type %%f
	consul kv put config/%%~nf/data %text%
)