# Database Design
## Tổng quan
Hệ thống sử dụng PostgreSQL để lưu trữ dữ liệu.

## Các bảng chính
### User
- id
- username
- email
- password
- role

### Job
- id
- title
- description
- company
- location
- salary
- employer_id

### CV
- id
- name
- file_url
- user_id

### Application
- id
- user_id
- job_id
- cv_id
- status
- applied_at

## Quan hệ
- User (1) —— (N) Job
- User (1) —— (N) CV
- User (1) —— (N) Application
- Job (1) —— (N) Application
- CV (1) —— (N) Application