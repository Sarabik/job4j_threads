package ru.job4j.asynchrony;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    private static Sums getSums(int ind, int[][] matrix) {
        int countRow = 0;
        for (int i : matrix[ind]) {
            countRow += i;
        }
        int countColumn = 0;
        for (int[] i : matrix) {
            countColumn += i[ind];
        }
        return new Sums(countRow, countColumn);
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = getSums(i, matrix);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i <= sums.length / 2; i++) {
            int leftInd = i;
            int rightInd = sums.length - 1 - i;
            sums[leftInd] = CompletableFuture.supplyAsync(
                    () -> getSums(leftInd, matrix)
            ).get();
            if (rightInd > leftInd) {
                sums[rightInd] = CompletableFuture.supplyAsync(
                        () -> getSums(rightInd, matrix)
                ).get();
            }
        }
        return sums;
    }
}
