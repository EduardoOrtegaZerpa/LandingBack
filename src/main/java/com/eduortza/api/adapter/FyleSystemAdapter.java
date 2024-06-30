package com.eduortza.api.adapter;


import com.eduortza.api.application.exception.FileManagerException;
import com.eduortza.api.application.port.out.FilePort;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Repository
public class FyleSystemAdapter implements FilePort {

    @Override
    public String saveFile(File file, String path) {
        createDirectoryIfNotExists(path);
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getName();
        try {
            Files.move(file.toPath(), new File(path + "/" + uniqueFileName).toPath());
            return uniqueFileName;
        } catch (Exception e) {
            throw new FileManagerException("Error saving file", e);
        }
    }

    @Override
    public void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public File createTempFile(MultipartFile file) {
        try {
            Path tempDir = Files.createTempDirectory("temp-dir");
            Path tempFile = tempDir.resolve(file.getOriginalFilename());
            file.transferTo(tempFile.toFile());
            return tempFile.toFile();
        } catch (Exception e) {
            throw new FileManagerException("Error saving file", e);
        }
    }
}
