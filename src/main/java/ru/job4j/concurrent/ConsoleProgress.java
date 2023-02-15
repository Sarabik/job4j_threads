package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        int changeSymbol = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                System.out.print("\r load: " + process[changeSymbol]);
                if (changeSymbol == 3) {
                    changeSymbol = 0;
                } else {
                    changeSymbol++;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
    }
}
