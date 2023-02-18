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
            char data;
            while ((data = (char) i.read()) > 0) {
                if (filter.test(data)) {
                    output.append(data);
                }
            }
            return output.toString();
        }
    }
}
