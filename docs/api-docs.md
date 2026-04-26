# API Documentation

## Authentication
### POST /api/auth/register
Đăng ký tài khoản

### POST /api/auth/login
Đăng nhập → trả JWT token

## Job API
### GET /api/jobs
Lấy danh sách job

### GET /api/jobs/{id}
Xem chi tiết job

### POST /api/jobs
Tạo job (EMPLOYER)

### PUT /api/jobs/{id}
Cập nhật job

### DELETE /api/jobs/{id}
Xóa job

## CV API
### GET /api/cvs
Lấy danh sách CV

### POST /api/cvs
Tạo CV
### PUT /api/cvs/{id}
Cập nhật CV

### DELETE /api/cvs/{id}
Xóa CV

## Application API
### POST /api/applications
Apply job

### GET /api/applications
Lấy danh sách application

### PUT /api/applications/{id}/status
Cập nhật trạng thái (APPROVED / REJECTED)

## Authorization
- USER: Apply job, quản lý CV
- EMPLOYER: Quản lý job, duyệt ứng viên
- ADMIN: Toàn quyền
## Response mẫu
{
  "message": "Success",
  "data": {},
  "status": 200
}