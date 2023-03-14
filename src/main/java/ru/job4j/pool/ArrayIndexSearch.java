package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ArrayIndexSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T element;

    public ArrayIndexSearch(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch(array, from, to, element);
        }
        int mid = (from + to) / 2;
        ArrayIndexSearch<T> left = new ArrayIndexSearch<>(array, from, mid, element);
        ArrayIndexSearch<T> right = new ArrayIndexSearch<>(array, mid + 1, to, element);
        left.fork();
        right.fork();
        int resultLeft = left.join();
        int resultRight = right.join();
        return Math.max(resultLeft, resultRight);
    }

    private static <T> int linearSearch(T[] array, int from, int to, T element) {
        int foundIndex = -1;
        for (int i = from; i <= to; i++) {
            if (element.equals(array[i])) {
                foundIndex = i;
                break;
            }
        }
        return foundIndex;
    }

    public static <T> int findIndex(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ArrayIndexSearch<>(array, 0, array.length - 1, element));
    }

    public static void main(String[] args) {
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        System.out.println(findIndex(array, "7"));
    }
}
