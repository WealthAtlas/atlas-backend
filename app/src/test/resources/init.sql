-- filepath: src/test/resources/init.sql
CREATE SCHEMA auth;
CREATE SCHEMA users;

CREATE USER auth_user WITH PASSWORD 'secret';
CREATE USER users_user WITH PASSWORD 'secret';

GRANT ALL PRIVILEGES ON SCHEMA auth TO auth_user;
GRANT ALL PRIVILEGES ON SCHEMA users TO users_user;