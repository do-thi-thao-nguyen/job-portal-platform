package com.jobportal.backend.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.backend.entity.CV;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.CVRepository;
import com.jobportal.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CVService {

    private final CVRepository cvRepository;
    private final UserRepository userRepository;

    public CV uploadCV(Long userId, MultipartFile file, String title) throws Exception {

        // 1. check user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. tạo đường dẫn tuyệt đối
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        // 3. tạo folder nếu chưa có
        File folder = new File(uploadDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 4. tạo tên file
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // 5. full path
        String filePath = uploadDir + fileName;

        // DEBUG
        System.out.println("Saving file to: " + filePath);

        // 6. lưu file
        file.transferTo(new File(filePath));

        // 7. lưu DB
        CV cv = new CV();
        cv.setUser(user);
        cv.setTitle(title);
        cv.setFileName(file.getOriginalFilename());
        cv.setContentType(file.getContentType());
        cv.setFileSize(file.getSize());

        // QUAN TRỌNG: lưu URL để frontend dùng
        cv.setFileUrl("/uploads/" + fileName);

        return cvRepository.save(cv);
    }
}