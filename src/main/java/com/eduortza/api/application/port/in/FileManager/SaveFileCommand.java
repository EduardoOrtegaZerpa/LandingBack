package com.eduortza.api.application.port.in.FileManager;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class SaveFileCommand {
    private File file;
    private String path;
}
