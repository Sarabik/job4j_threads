package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.job4j.pool.ArrayIndexSearch.findIndex;

class ArrayIndexSearchTest {

    @Test
    public void whenSearchString() {
        String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int index1 = findIndex(array, "7");
        int index2 = findIndex(array, "12");
        assertThat(index1).isEqualTo(7);
        assertThat(index2).isEqualTo(-1);
    }

    @Test
    public void whenSearchInteger() {
        Integer[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int index1 = findIndex(array, 7);
        int index2 = findIndex(array, 12);
        assertThat(index1).isEqualTo(7);
        assertThat(index2).isEqualTo(-1);
    }

    @Test
    public void whenArrayLengthIsSmallerThan10ThenLinearSearch() {
        String[] array = {"0", "1", "2", "3"};
        int index1 = findIndex(array, "2");
        int index2 = findIndex(array, "12");
        assertThat(index1).isEqualTo(2);
        assertThat(index2).isEqualTo(-1);
    }

    @Test
    public void whenArrayIsEmpty() {
        String[] array = {};
        assertThatThrownBy(() -> findIndex(array, "7")).isInstanceOf(IllegalArgumentException.class);
    }
}