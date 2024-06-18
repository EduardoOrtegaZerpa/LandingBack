package com.eduortza.api.application.port.out;

import java.io.File;

public interface FilePort {
    String saveFile(File file, String path);
    void deleteFile(String path);
}
