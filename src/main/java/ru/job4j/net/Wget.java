package ru.job4j.net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    /* speed = n byte per sec */
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long currentTime = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long difference = System.currentTimeMillis() - currentTime;
                if (difference < bytesRead / speed * 1000L) {
                    Thread.sleep(bytesRead / speed * 1000L - difference);
                }
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
        if (args.length != 2) {
            throw new IllegalArgumentException("Illegal argument count");
        }
        String url = args[0];
        urlValidator(url);
        int speed = Integer.parseInt(args[1]);
        speedValidator(speed);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
