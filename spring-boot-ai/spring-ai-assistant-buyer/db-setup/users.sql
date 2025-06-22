CREATE ROLE springai WITH LOGIN PASSWORD 'unhackable';
CREATE DATABASE springaidb OWNER springai;
GRANT ALL PRIVILEGES ON DATABASE springaidb TO springai;
GRANT ALL PRIVILEGES ON DATABASE postgresml TO springai;
GRANT USAGE ON SCHEMA public TO springai;
GRANT CREATE ON SCHEMA public TO springai;
GRANT USAGE ON SCHEMA public TO springai;
GRANT CREATE ON SCHEMA pgml TO springai;
GRANT USAGE ON SCHEMA pgml TO springai;