package ru.job4j.asynchrony;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {
    @Test
    public void whenUseSimpleSum() {
        int[][] matrix = {{1, 2}, {3, 4}};
        RolColSum.Sums[] expected = {new RolColSum.Sums(3, 4), new RolColSum.Sums(7, 6)};
        assertThat(RolColSum.sum(matrix)).isEqualTo(expected);
    }

    @Test
    public void whenUseAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2}, {3, 4}};
        RolColSum.Sums[] expected = {new RolColSum.Sums(3, 4), new RolColSum.Sums(7, 6)};
        assertThat(RolColSum.asyncSum(matrix)).isEqualTo(expected);
    }
}