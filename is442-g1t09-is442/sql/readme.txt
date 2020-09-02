How to run SQL Script:
1. Prerequisites:
- Assume that you username is "root" and password is "" 
- MySQL server is running
- JDBC is in the lib file

2. Running SQL file:
- Go to the directory where deploy.sql is located
- run "mysql -u root -p < deploy.sql"

3. Verification:
- run compile.bat & run.bat
- Login as username: "job" and password: "password"

- Extra (for powershell)
- run "mysql -u root -p" first
- then run "source deploy.sql"
