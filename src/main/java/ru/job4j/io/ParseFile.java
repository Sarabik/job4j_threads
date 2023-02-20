package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private synchronized String getContent(Predicate<Character> filter) throws IOException {
        try (BufferedReader i = new BufferedReader(new FileReader(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }
}
