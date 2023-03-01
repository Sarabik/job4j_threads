package ru.job4j.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CacheTest {
    final Cache cache = new Cache();

    @BeforeEach
    public void init() {
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 1));
    }

    @Test
    public void whenAddSame() {
        assertThat(cache.add(new Base(1, 1))).isFalse();
    }

    @Test
    public void whenSuccessfullyUpdated() {
        Base b = new Base(2, 1);
        b.setName("name");
        cache.update(b);
        assertThat(cache.get(2).getVersion()).isEqualTo(2);
        assertThat(cache.get(2).getName()).isEqualTo("name");
    }

    @Test
    public void whenNotSuccessfullyUpdated() {
        Base b = new Base(2, 2);
        assertThatThrownBy(() -> cache.update(b))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    public void whenSuccessfullyDeleted() {
        cache.delete(new Base(2, 1));
        assertThat(cache.get(2)).isNull();
    }
}