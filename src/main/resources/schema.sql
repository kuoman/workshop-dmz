-- Create database (run this manually in PostgreSQL if database doesn't exist)
-- CREATE DATABASE accounts_db;

-- Create accounts table
CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    address TEXT NOT NULL
);