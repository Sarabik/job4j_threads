package ru.job4j.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NioDemo {
    /* чтение из файла в буфер при помощи канала с последующим выводом данных на консоль */
    public static void main(String[] args) throws Exception {
        int count;
        /* создаем канал byteChannel и буфер buffer */
        try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get("data/nio.txt"))) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            /* читаем данные в буфер */
            do {
                count = byteChannel.read(buffer);
                if (count != -1) {
                    /* rewind() подготавливает буфер к считыванию из него данных -
                    устанавливает курсор в нулевую позицию, так как после вызова метода read()
                    курсор будет находится в конце буфера */
                    buffer.rewind();
                    for (int i = 0; i < count; i++) {
                        System.out.print((char) buffer.get());
                    }
                }
            } while (count != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
