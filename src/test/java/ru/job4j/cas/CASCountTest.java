package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void whenTestCounterWithTwoThreads() throws InterruptedException {
        final CASCount counter = new CASCount();
        Runnable task = () -> {
            for (int i = 0; i < 15; i++) {
                counter.increment();
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(counter.get()).isEqualTo(30);
    }
}