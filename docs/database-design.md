# Database Design
## Tổng quan
Hệ thống sử dụng **MySQL** làm hệ quản trị cơ sở dữ liệu để lưu trữ thông tin người dùng, công việc, CV và đơn ứng tuyển.

Cơ sở dữ liệu được thiết kế theo mô hình quan hệ (Relational Database), đảm bảo tính toàn vẹn dữ liệu và dễ dàng mở rộng.

## Các bảng chính
### User
| Tên cột | Kiểu dữ liệu | Mô tả |
|-------- |------------- |------ |
| id      | Long         | Khoá chính |
| email   | String       | Email đăng nhập |
| password| String       | Mật khẩu |
| role    | String       | USER / EMPLOYER / ADMIN |

### Job
| Tên cột | Kiểu dữ liệu | Mô tả |
|-------- |------------  |-----  |
| id      | Long         | Khoá chính |
| title   | String       | Tên công việc |
| description | String   | Mô tả |
| salaryMin| Double      | Lương tối thiểu |
| salaryMax| Double      | Lương tối đa |
| location | String      | Địa điểm |
| employer_id | Long     | FK → User |


### CV
| Tên cột | Kiểu dữ liệu | Mô tả |
|-------- |------------- |------|
| id      | Long         | Khoá chính |
| fileName| String       | Tên file CV |
| user_id | Long         | FK → User |
### Application
| Tên cột | Kiểu dữ liệu | Mô tả |
|-------- |------------- |------|
| id | Long | Khoá chính |
| user_id | Long         | FK → User |
| job_id  | Long         | FK → Job |
| cvUrl   | String       | Đường dẫn file CV |
| status  | Enum         | PENDING / APPROVED / REJECTED |
| appliedAt | DateTime   | Thời gian ứng tuyển |

## Quan hệ
- User (1) —— (N) Job
- User (1) —— (N) CV
- User (1) —— (N) Application
- Job (1) —— (N) Application
- CV (1) —— (N) Application

## 4. Mô tả thiết kế

Hệ thống được thiết kế theo mô hình quan hệ, trong đó:

- Mỗi **User** có thể tạo nhiều CV và thực hiện nhiều đơn ứng tuyển  
- Mỗi **Job** có thể có nhiều ứng viên ứng tuyển  
- Bảng **Application** đóng vai trò trung gian, liên kết giữa User và Job  
- Trạng thái ứng tuyển được lưu dưới dạng Enum để dễ quản lý  

Thiết kế này giúp đảm bảo:
- Tính nhất quán dữ liệu  
- Dễ dàng mở rộng chức năng  
- Tối ưu truy vấn khi lấy danh sách ứng tuyển  

---

## 5. Ghi chú

- File CV được lưu trên server (thư mục uploads), database chỉ lưu đường dẫn (cvUrl)
- Hệ thống hỗ trợ nhiều trạng thái ứng tuyển để theo dõi tiến trình
- Có kiểm tra tránh duplicate application (apply trùng)