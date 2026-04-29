# job-portal-platform
## Mô tả
Hệ thống website tuyển dụng giúp kết nối nhà tuyển dụng và ứng viên. Ứng viên có thể tìm kiếm việc làm, nộp CV và theo dõi trạng thái ứng tuyển. Nhà tuyển dụng có thể đăng tin tuyển dụng và quản lý danh sách ứng viên.

Hệ thống hỗ trợ:
- Ứng viên tìm kiếm việc làm, upload CV và ứng tuyển trực tuyến
- Theo dõi trạng thái ứng tuyển (PENDING / APPROVED / REJECTED)
- Nhà tuyển dụng đăng tin tuyển dụng và quản lý ứng viên

Đề tài hướng đến giải quyết vấn đề thực tế:
- Sinh viên khó tìm việc phù hợp
- Doanh nghiệp nhỏ khó tiếp cận nền tảng tuyển dụng lớn

## Thành viên nhóm
|MSSV|Họ tên|Vai trò|
|2251010064|Đỗ Thị Thảo Nguyên|Fullstack Developer & Database Manager|
|2251010068|Trương Ngọc Xuân Nhi|Fullstack Developer & Database Manager|

## Công nghệ sử dụng
- Backend: Spring Boot (Java)
- Frontend: ReactJS
- Database: MySQL
- Authentication: Spring Security + JWT
- API: RESTful API
- Tool: Postman, Git

## Cài đặt và chạy
### Yêu cầu
- Java 17+
- Node.js 18+
- MySQL
### Chạy Backend
cd backend
./mvnw spring-boot:run

### Chạy Frontend
cd frontend
npm install
npm start

### Truy cập hệ thống
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080

## Tính năng chính
### Ứng viên (Candidate)
- Đăng ký tài khoản và đăng nhập hệ thống
- Xác thực người dùng bằng JWT (JSON Web Token)
- Tìm kiếm công việc theo từ khóa (job title, description)
- Xem chi tiết thông tin tuyển dụng
- Ứng tuyển công việc (Apply Job) thông qua hệ thống
- Lựa chọn và nộp CV khi ứng tuyển
- Quản lý CV cá nhân
- Xem danh sách các đơn đã ứng tuyển (Application History)
- Theo dõi trạng thái đơn ứng tuyển: PENDING / APPROVED / REJECTED
### Nhà tuyển dụng (Employer)
- Đăng ký và đăng nhập với vai trò EMPLOYER
- Tạo mới tin tuyển dụng (Job Posting)
- Cập nhật và xóa tin tuyển dụng
- Xem danh sách các job đã đăng
- Xem danh sách ứng viên ứng tuyển theo từng job
- Duyệt (APPROVED) hoặc từ chối (REJECTED) hồ sơ ứng viên
- Quản lý trạng thái đơn ứng tuyển (Application Management)

### Hệ thống (System)
- Xây dựng API theo kiến trúc RESTful
- Xác thực và phân quyền bằng Spring Security + JWT
- Phân quyền người dùng: USER / EMPLOYER / ADMIN
- Quản lý dữ liệu với MySQL
- Validate dữ liệu đầu vào (Request Validation)
- Xử lý lỗi và trả về response chuẩn (Exception Handling)

## Demo
- Video demo: ...
- Screenshots: ...

## Tài liệu
- [Phân tích yêu cầu](docs/requirements.md)
- [Database Design](docs/database-design.md)
- [API Documentation](docs/api-docs.md) 

## Cấu trúc thư mục
job-portal-platform/
│── backend/
│── frontend/
│── docs/
│── README.md



