package ru.job4j.asynchrony;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RolColSumV2 {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
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

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        Arrays.fill(sums, new Sums());
        for (int i = 0; i < sums.length; i++) {
            sums[i].setRowSum(getRowSum(i, matrix));
            sums[i].setColSum(getColSum(i, matrix));
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        Arrays.fill(sums, new Sums());
        for (int i = 0; i < sums.length; i++) {
            int index = i;
            CompletableFuture.runAsync(
                    () -> sums[index].setRowSum(getRowSum(index, matrix))
            );
            CompletableFuture.runAsync(
                    () -> sums[index].setColSum(getColSum(index, matrix))
            );
        }
        return sums;
    }
}
