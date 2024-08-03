package com.eduortza.api.application.port.out;

import com.eduortza.api.application.exception.FileManagerException;

import java.io.File;

public interface FilePort {
    String saveFile(File file);
    void deleteFile(String fileName);
}
