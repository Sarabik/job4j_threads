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
        int foundIndex = -1;
        if (from == to) {
            if (element.equals(array[from])) {
                foundIndex = from;
            }
        } else {
            int mid = (from + to) / 2;
            ArrayIndexSearch<T> left = new ArrayIndexSearch<>(array, from, mid, element);
            ArrayIndexSearch<T> right = new ArrayIndexSearch<>(array, mid + 1, to, element);
            left.fork();
            right.fork();
            int resultLeft = left.join();
            int resultRight = right.join();
            foundIndex = Math.max(resultLeft, resultRight);
        }
        return foundIndex;
    }

    private static <T> int linearSearch(T[] array, T element) {
        int foundIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (element.equals(array[i])) {
                foundIndex = i;
                break;
            }
        }
        return foundIndex;
    }

    public static <T> int findIndex(T[] array, T element) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        int foundIndex;
        if (array.length <= 10) {
            foundIndex = linearSearch(array, element);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            foundIndex = forkJoinPool.invoke(new ArrayIndexSearch<>(array, 0, array.length - 1, element));
        }
        return foundIndex;
    }

    public static void main(String[] args) {
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        System.out.println(findIndex(array, "7"));
    }
}
