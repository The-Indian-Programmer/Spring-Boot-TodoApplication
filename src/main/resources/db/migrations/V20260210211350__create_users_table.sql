-- Migration: create_users_table
CREATE TABLE users (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       user_name VARCHAR(255) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL,
       is_active BOOLEAN NOT NULL DEFAULT TRUE,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
