package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /* send email */
    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s",
                    user.getUsername(), user.getEmail());
            String body = String.format("Add a new event to %s", user.getUsername());
            send(subject, body, user.getEmail());
        });
    }

    /* close pool */
    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
    }
}