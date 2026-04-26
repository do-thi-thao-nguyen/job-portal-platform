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


INSERT INTO user (email, password, role)
VALUES ('admin@gmail.com', '123456', 'ADMIN');
ALTER TABLE users ADD CONSTRAINT unique_email UNIQUE (email);


USE jobportal;
SET SQL_SAFE_UPDATES = 0;
DELETE FROM users WHERE email = 'admin@gmail.com';

ALTER TABLE user ADD UNIQUE (email);

USE jobportal;
SELECT * FROM user WHERE email = 'admin@gmail.com';

SELECT * FROM users WHERE email = 'admin@gmail.com';


SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM users;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO users (email, password, role)
VALUES ('admin@gmail.com', '123456', 'ADMIN');


DELETE FROM users WHERE email = 'user@gmail.com';
ALTER TABLE users ADD CONSTRAINT unique_email UNIQUE (email);

SET FOREIGN_KEY_CHECKS=0;
TRUNCATE company_package;
SET FOREIGN_KEY_CHECKS=1;

SELECT email, COUNT(*)
FROM users
GROUP BY email
HAVING COUNT(*) > 1;

SELECT * FROM users WHERE email = 'admin@gmail.com';





SELECT email, COUNT(*)
FROM users
GROUP BY email
HAVING COUNT(*) > 1;

SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM users WHERE email = 'admin@gmail.com';

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO users (email, password, role)
VALUES ('admin@gmail.com', '123456', 'ADMIN');

ALTER TABLE users ADD CONSTRAINT unique_email UNIQUE (email);


USE jobportal;
SELECT role FROM users WHERE id = 5;

SELECT * FROM users;

SELECT * FROM companies WHERE employer_id = 33;
SELECT * FROM jobs WHERE company_id IN (
    SELECT id FROM companies WHERE employer_id = 33
);

SELECT * FROM companies WHERE user_id = 33;

SELECT * FROM users;
SELECT * FROM companies;
SELECT * FROM job;
select*from categories;






INSERT INTO categories(name) VALUES ('Công nghệ thông tin');
INSERT INTO categories(name) VALUES ('Marketing');
INSERT INTO categories(name) VALUES ('Tài chính - Ngân hàng');
INSERT INTO categories(name) VALUES ('Kế toán - Kiểm toán');
INSERT INTO categories(name) VALUES ('Nhân sự');
INSERT INTO categories(name) VALUES ('Kinh doanh / Bán hàng');
INSERT INTO categories(name) VALUES ('Chăm sóc khách hàng');
INSERT INTO categories(name) VALUES ('Thiết kế đồ họa');
INSERT INTO categories(name) VALUES ('Giáo dục - Đào tạo');
INSERT INTO categories(name) VALUES ('Y tế - Chăm sóc sức khỏe');
INSERT INTO categories(name) VALUES ('Kỹ thuật - Cơ khí');
INSERT INTO categories(name) VALUES ('Xây dựng');
INSERT INTO categories(name) VALUES ('Logistics - Chuỗi cung ứng');
INSERT INTO categories(name) VALUES ('Xuất nhập khẩu');
INSERT INTO categories(name) VALUES ('Luật - Pháp lý');
INSERT INTO categories(name) VALUES ('Du lịch - Khách sạn');
INSERT INTO categories(name) VALUES ('Nhà hàng - Ẩm thực');
INSERT INTO categories(name) VALUES ('Truyền thông - Báo chí');
INSERT INTO categories(name) VALUES ('Sản xuất');
INSERT INTO categories(name) VALUES ('Quản lý điều hành');
INSERT INTO categories(name) VALUES ('Bất động sản');
INSERT INTO categories(name) VALUES ('Bảo hiểm');
INSERT INTO categories(name) VALUES ('Ngân hàng');
INSERT INTO categories(name) VALUES ('Hành chính - Văn phòng');
INSERT INTO categories(name) VALUES ('Nông nghiệp');
INSERT INTO categories(name) VALUES ('Môi trường');
INSERT INTO categories(name) VALUES ('Nghiên cứu & Phát triển (R&D)');
INSERT INTO categories(name) VALUES ('Kiểm soát chất lượng (QA/QC)');
INSERT INTO categories(name) VALUES ('An ninh - Bảo vệ');
INSERT INTO categories(name) VALUES ('Lao động phổ thông');



