package com.eduortza.api.application.service;

import com.eduortza.api.application.port.in.FileManager.SaveFileCommand;
import com.eduortza.api.application.port.in.FileManager.SaveFilePort;
import com.eduortza.api.application.port.out.FilePort;

import java.io.IOException;

public class FileService implements SaveFilePort {

    private final FilePort filePort;

    public FileService(FilePort filePort) {
        this.filePort = filePort;
    }

    @Override
    public void saveFile(SaveFileCommand command) throws IOException {
        filePort.saveFile(command.getFile(), command.getPath());
    }
}
