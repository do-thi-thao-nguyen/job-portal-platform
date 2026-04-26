# Requirements Analysis
## Giới thiệu
Hệ thống Job Portal Platform là website hỗ trợ kết nối giữa ứng viên và nhà tuyển dụng.

## Đối tượng sử dụng
- Candidate
- Employer
- Admin

## Chức năng chính
### Ứng viên
- Đăng ký / đăng nhập
- Tìm kiếm việc làm
- Xem chi tiết job
- Nộp đơn ứng tuyển
- Quản lý CV
- Theo dõi trạng thái ứng tuyển

### Nhà tuyển dụng
- Đăng tin tuyển dụng
- Quản lý job
- Xem danh sách ứng viên
- Duyệt / từ chối hồ sơ

### Hệ thống
- Xác thực JWT
- Phân quyền USER / EMPLOYER / ADMIN
- REST API
### Yêu cầu phi chức năng
- Bảo mật bằng JWT
- Hệ thống phản hồi nhanh
- Dễ sử dụng
- Có khả năng mở rộng
### Use Case (tóm tắt)
|  Actor  |  Use Case  |
|Candidate|Apply Job   |
|Employer |Manage Job  | 
|Admin	  |Manage Users|