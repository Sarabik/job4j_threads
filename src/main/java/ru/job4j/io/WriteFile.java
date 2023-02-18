package ru.job4j.io;

import java.io.*;

public class WriteFile {
    private final File file;

    public WriteFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter o = new BufferedWriter(new FileWriter(file))) {
            o.write(content);
        }
    }
}
