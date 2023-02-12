package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        /* new */
        System.out.println(first.getName() + ": " + first.getState());
        System.out.println(second.getName() + ": " + second.getState());
        /* start */
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED ||
                second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + ": " + first.getState());
            System.out.println(second.getName() + ": " + first.getState());
        }
        /* terminated */
        System.out.println(first.getName() + ": " + first.getState());
        System.out.println(second.getName() + ": " + first.getState());
        /* finished */
        System.out.println("Job is finished");
    }
}
