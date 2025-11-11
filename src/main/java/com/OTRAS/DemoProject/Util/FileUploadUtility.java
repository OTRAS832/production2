//package com.OTRAS.DemoProject.Util;
//
//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//@Component
//@RequiredArgsConstructor
//public class FileUploadUtility {
//
//    private final Storage storage;
//
//    private static final String BUCKET_NAME = "otras-files";  // ✅ Your bucket
//    private static final String BASE_URL = "https://storage.googleapis.com/otras-files/";
//
//    // ✅ Upload new file
//    public String uploadFile(MultipartFile file, String folderName) {
//        try {
//            String original = file.getOriginalFilename();
//            String uniqueName = System.currentTimeMillis() + "_" + original;
//
//            String objectName = folderName + "/" + uniqueName;
//
//            BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
//            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
//                    .setContentType(file.getContentType())
//                    .build();
//
//            storage.create(blobInfo, file.getBytes());
//
//            return BASE_URL + objectName;
//
//        } catch (Exception e) {
//            throw new RuntimeException("File upload failed: " + e.getMessage());
//        }
//    }
//
//    // ✅ Replace existing file
//    public String replaceFile(MultipartFile newFile, String folderName, String oldFileUrl) {
//        if (oldFileUrl != null && !oldFileUrl.isBlank()) {
//            deleteFile(oldFileUrl);
//        }
//        return uploadFile(newFile, folderName);
//    }
//
//    // ✅ Delete
//    public void deleteFile(String fileUrl) {
//        try {
//            String objectName = fileUrl.replace(BASE_URL, "");
//            BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
//            storage.delete(blobId);
//
//        } catch (Exception e) {
//            System.err.println("Failed to delete file: " + e.getMessage());
//        }
//    }
//
//    public String uploadCandidateDocument(MultipartFile file, Long candidateId, String docType) {
//        return uploadFile(file, "candidate_" + candidateId);
//    }
//
//	
//}


package com.OTRAS.DemoProject.Util;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileUploadUtility {

    private final Storage storage;

    private static final String BUCKET_NAME = "otras-files";
    private static final String BASE_URL = "https://storage.googleapis.com/otras-files/";

    // ✅ 1) Upload with auto-generated filename
    public String uploadFile(MultipartFile file, String folderName) {
        String autoName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        return uploadFile(file, folderName, autoName);
    }

    // ✅ 2) Upload with custom filename
    public String uploadFile(MultipartFile file, String folderName, String fileName) {
        try {
            String objectName = folderName + "/" + fileName;

            BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            storage.create(blobInfo, file.getBytes());
            return BASE_URL + objectName;

        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    // ✅ 3) Replace file → delete old & upload with custom filename
    public String replaceFile(
            MultipartFile newFile,
            String folderName,
            String fileName,
            String oldFileUrl
    ) {
        if (oldFileUrl != null && !oldFileUrl.isBlank()) {
            deleteFile(oldFileUrl);
        }
        return uploadFile(newFile, folderName, fileName);
    }

    // ✅ 4) Replace file → delete old & upload with auto-generated filename
    public String replaceFile(
            MultipartFile newFile,
            String folderName,
            String oldFileUrl
    ) {
        if (oldFileUrl != null && !oldFileUrl.isBlank()) {
            deleteFile(oldFileUrl);
        }
        String autoName = System.currentTimeMillis() + "_" + newFile.getOriginalFilename();
        return uploadFile(newFile, folderName, autoName);
    }

    // ✅ 5) Delete file
    public void deleteFile(String fileUrl) {
        try {
            String objectName = fileUrl.replace(BASE_URL, "");
            BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
            storage.delete(blobId);

        } catch (Exception e) {
            System.err.println("Failed to delete file: " + e.getMessage());
        }
    }

    // ✅ 6) Candidate document upload
    public String uploadCandidateDocument(MultipartFile file, Long candidateId, String originalName) {
        String finalName = System.currentTimeMillis() + "_" + originalName;
        String folder = "candidate_" + candidateId;
        return uploadFile(file, folder, finalName);
    }
}
