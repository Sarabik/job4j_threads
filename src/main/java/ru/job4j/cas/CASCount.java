package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int temp;
        int incremented;
        do {
            temp = count.get();
            incremented = temp + 1;
        } while (!count.compareAndSet(temp, incremented));
    }

    public int get() {
        return count.get();
    }
}
