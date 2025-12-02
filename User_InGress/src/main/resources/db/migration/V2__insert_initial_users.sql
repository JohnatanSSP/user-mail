-- V2__insert_initial_users.sql
-- Insert initial seed users into USERS_TB

-- Use gen_random_uuid() (pgcrypto extension) to generate UUIDs
INSERT INTO USERS_TB (UserId, name, email) VALUES (gen_random_uuid(), 'Alice Smith', 'alice.smith@example.com');
INSERT INTO USERS_TB (UserId, name, email) VALUES (gen_random_uuid(), 'Bob Johnson', 'bob.johnson@example.com');
INSERT INTO USERS_TB (UserId, name, email) VALUES (gen_random_uuid(), 'Carol Williams', 'carol.williams@example.com');

