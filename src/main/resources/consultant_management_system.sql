-- Consultant Management System
-- Database and Table Setup

CREATE DATABASE IF NOT EXISTS consultant_management_system;

USE consultant_management_system;

-- Create consultant table

CREATE TABLE IF NOT EXISTS consultant (
                                          id BIGINT NOT NULL AUTO_INCREMENT,
                                          email VARCHAR(255),
    experience INT NOT NULL,
    name VARCHAR(255),
    phone VARCHAR(255),
    technology VARCHAR(255),
    active BIT(1) NOT NULL,
    created_date DATE,
    PRIMARY KEY (id)
    );

-- Sample Consultant Data

INSERT INTO consultant
(email, experience, name, phone, technology, active, created_date)
VALUES
    ('alex.johnson@gmail.com', 9, 'Alex Johnson', '917-555-1023', 'Angular', 1, '2026-08-06'),
    ('michael.brown@gmail.com', 4, 'Michael Brown', '917-555-1023', 'AWS Cloud', 0, '2026-08-06'),
    ('emily.davis012@gmail.com', 8, 'Emily Davis', '718556234', 'Angular', 0, '2026-08-06'),
    ('kimarachang65@gmail.com', 7, 'Kimara Chang', '917-555-1222', 'Java Spring Boot', 1, '2026-08-06'),
    ('wilsondani.23@gmail.com', 4, 'Daniel Wilson', '918-232-667', 'Spring Boot', 1, '2026-08-06');