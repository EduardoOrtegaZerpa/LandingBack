package com.eduortza.api.application.port.out;

import java.io.File;

public interface FilePort {
    String saveFile(File file) throws Exception;
    void deleteFile(String fileName) throws Exception;
}
