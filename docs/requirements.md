# Requirements Analysis
## Giới thiệu
Hệ thống Job Portal Platform là website hỗ trợ kết nối giữa ứng viên và nhà tuyển dụng.

Hệ thống cho phép ứng viên tìm kiếm việc làm, quản lý CV và ứng tuyển trực tuyến, đồng thời hỗ trợ nhà tuyển dụng đăng tin tuyển dụng và quản lý hồ sơ ứng viên.

## Đối tượng sử dụng

- **Candidate (Ứng viên):** Tìm việc, ứng tuyển, quản lý CV
- **Employer (Nhà tuyển dụng):** Đăng tin, quản lý job, xử lý hồ sơ
- **Admin (Quản trị viên):** Quản lý hệ thống và người dùng

## Chức năng chính
### Ứng viên
- Đăng ký / đăng nhập hệ thống
- Tìm kiếm việc làm theo từ khóa
- Xem chi tiết công việc
- Upload CV (PDF)
- Ứng tuyển trực tuyến (Apply Job)
- Quản lý nhiều phiên bản CV
- Xem danh sách job đã ứng tuyển
- Theo dõi trạng thái ứng tuyển (PENDING / APPROVED / REJECTED)

### Nhà tuyển dụng
- Đăng nhập hệ thống
- Tạo / sửa / xóa tin tuyển dụng (CRUD Job)
- Xem danh sách job đã đăng
- Xem danh sách ứng viên theo từng job
- Duyệt (APPROVED) hoặc từ chối (REJECTED) hồ sơ

### Hệ thống

- Xây dựng API theo kiến trúc RESTful
- Xác thực người dùng bằng JWT
- Phân quyền USER / EMPLOYER / ADMIN
- Upload file CV (MultipartFile)
- Kiểm tra định dạng file PDF và kích thước
- Lưu trữ dữ liệu bằng MySQL
### Yêu cầu phi chức năng
- Bảo mật: Sử dụng JWT để xác thực và phân quyền
- Hiệu năng: Hệ thống phản hồi nhanh với các request
- Khả dụng: Giao diện thân thiện, dễ sử dụng
- Mở rộng: Kiến trúc RESTful dễ dàng mở rộng
### Use Case (tóm tắt)
| Actor     | Use Case                          |
| Candidate | Apply Job, Manage CV              |
| Employer  | Manage Job, Review Application    |
| Admin     | Manage Users, Manage System       |

## 6. Luồng hoạt động chính

```text
User → Frontend → Backend → Database → Response