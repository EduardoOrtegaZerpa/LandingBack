package com.eduortza.api.application.port.in.FileManager;

import java.io.IOException;

public interface SaveFilePort {
    void saveFile(SaveFileCommand command) throws IOException;
}
