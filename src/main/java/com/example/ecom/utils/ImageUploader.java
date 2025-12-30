package com.example.ecom.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils; // Import thư viện này
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID; // Import UUID

public class ImageUploader {
    private static final String UPLOAD_DIR = "uploads/images/";

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/bmp",
            "image/webp"
    );

    private MultipartFile multipartFile;
    private String cleanImageType; // Tên thư mục con đã được làm sạch
    private String storedFileName; // Tên file độc nhất để lưu
    private boolean hasFileToUpload;

    public ImageUploader(MultipartFile multipartFile, String imageType) {
        this.multipartFile = multipartFile;

        // 1. Kiểm tra xem có file không
        if (multipartFile == null || multipartFile.isEmpty()) {
            this.hasFileToUpload = false;
            return; // Không cần làm gì thêm
        }

        this.hasFileToUpload = true;

        // 2. Làm sạch 'imageType' để chống Path Traversal
        // "users/profiles" -> "usersprofiles"
        // "../users" -> "users"
        if (imageType != null) {
            // Xóa các ký tự nguy hiểm, chỉ giữ lại phần tên hợp lệ
            this.cleanImageType = StringUtils.cleanPath(imageType);
        } else {
            this.cleanImageType = "general"; // Thư mục mặc định nếu không cung cấp
        }

        // 3. Khởi tạo tên file an toàn (sử dụng UUID)
        String fileName = multipartFile.getOriginalFilename();
        String originalName = fileName != null ? StringUtils.cleanPath(fileName) : "";
        String extension = "";
        if (!originalName.isEmpty() && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        this.storedFileName = UUID.randomUUID().toString() + extension;
    }

    private boolean isImageFile() {
        if (!this.hasFileToUpload || multipartFile.getContentType() == null) {
            return false;
        }
        return ALLOWED_IMAGE_TYPES.contains(multipartFile.getContentType().toLowerCase());
    }

    /**
     * Upload file
     * @return Đường dẫn tương đối (ví dụ: "uploads/images/users/abc-123.png")
     * hoặc NULL nếu không có file.
     */
    public String upload() throws Exception {
        if (!this.hasFileToUpload) {
            return null; // Không có file để upload
        }

        // *** VALIDATION ***
        if (!isImageFile()) {
            throw new Exception("Lỗi upload: Chỉ chấp nhận file ảnh.");
        }
        // *** KẾT THÚC VALIDATION ***

        // Đây là đường dẫn đầy đủ tới thư mục con
        Path uploadPath = Paths.get(UPLOAD_DIR, this.cleanImageType);

        // Đây là đường dẫn tương đối sẽ lưu vào DB
        String relativePath = UPLOAD_DIR + this.cleanImageType + "/" + this.storedFileName;

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(this.storedFileName);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new Exception("Could not upload file: " + this.storedFileName, e);
        }

        // Trả về đường dẫn chính xác đã lưu file
        return relativePath;
    }
}