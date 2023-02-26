package ru.job4j.producerconsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void offer(T value) {
        while (queue.size() == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        T result = queue.poll();
        notify();
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            int i = 0;
            while (i < 20) {
                try {
                blockingQueue.offer(i);
                System.out.println(String.format(("%d is offered"), i));
                i++;
                Thread.sleep(700);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread consumer = new Thread(() -> {
            int i = 0;
            while (i < 20) {
                try {
                    int a = blockingQueue.poll();
                    System.out.println(String.format(("%d is polled"), a));
                    i++;
                    Thread.sleep(1200);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
