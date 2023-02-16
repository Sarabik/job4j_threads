package ru.job4j.net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String outputFile;
    /* speed = n Kb per sec */
    private final int speed;

    public Wget(String url, String outputFile, int speed) {
        this.url = url;
        this.outputFile = outputFile;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (downloadData >= speed * 1024) {
                    long difference = System.currentTimeMillis() - startTime;
                    if (difference < 1000) {
                        Thread.sleep(1000 - difference);
                    }
                    downloadData = 0;
                    startTime = System.currentTimeMillis();
                }
                downloadData += bytesRead;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void urlValidator(String url) {
        try {
            new URL(url).toURI();
        } catch (URISyntaxException | MalformedURLException exception) {
            throw new IllegalArgumentException("URL is not correct");
        }
    }

    public static void speedValidator(int speed) {
        if (speed <= 0) {
            throw new RuntimeException("Speed must be greater than zero");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Illegal argument count");
        }
        String url = args[0];
        urlValidator(url);
        String outputFile = args[1];
        int speed = Integer.parseInt(args[2]);
        speedValidator(speed);
        Thread wget = new Thread(new Wget(url, outputFile, speed));
        wget.start();
        wget.join();
    }
}
