-- V1__create_tb_users.sql
-- Create extension for generating UUIDs and the USERS_TB table

-- Create pgcrypto extension if not exists (required for gen_random_uuid)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS USERS_TB (
    UserId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

