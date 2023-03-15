package ru.job4j.asynchrony;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    private static int getRowSum(int ind, int[][] matrix) {
        int result = 0;
        for (int i : matrix[ind]) {
            result += i;
        }
        return result;
    }

    private static int getColSum(int ind, int[][] matrix) {
        int result = 0;
        for (int[] i : matrix) {
            result += i[ind];
        }
        return result;
    }

    private static Sums getSums(int ind, int[][] matrix) {
        return new Sums(getRowSum(ind, matrix), getColSum(ind, matrix));
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
