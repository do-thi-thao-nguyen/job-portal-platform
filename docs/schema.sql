CREATE DATABASE jobportal;
USE jobportal;
SELECT * FROM users;

CREATE TABLE jobs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    company VARCHAR(255),
    salary DOUBLE
);

USE jobportal;
ALTER TABLE jobs DROP COLUMN company;
ALTER TABLE jobs ADD COLUMN company_id BIGINT;


USE jobportal;
SET SQL_SAFE_UPDATES = 0;
DELETE FROM users WHERE email = 'admin@gmail.com';


USE jobportal;
SELECT * FROM user;

ALTER TABLE user ADD COLUMN email VARCHAR(255);
ALTER TABLE user ADD COLUMN role VARCHAR(50);
UPDATE user 
SET email = 'admin@gmail.com'
WHERE id = 1;

UPDATE user 
SET role = 'ADMIN'
WHERE id = 1;

USE jobportal;
UPDATE user 
SET role = 'ADMIN'
WHERE email = 'admin@gmail.com';
