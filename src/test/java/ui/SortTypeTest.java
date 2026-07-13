package ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SortType — типы сортировки")
class SortTypeTest {

    @Test
    @DisplayName("Содержит ровно 2 значения")
    void shouldHaveTwoValues() {
        assertEquals(2, SortType.values().length);
    }

    @Test
    @DisplayName("NORMAL содержит 'Обычная'")
    void normalShouldContainOrdinary() {
        assertTrue(SortType.NORMAL.getDescription().contains("Обычная"));
    }

    @Test
    @DisplayName("EVEN содержит 'чётн'")
    void evenShouldContainEven() {
        assertTrue(SortType.EVEN.getDescription().toLowerCase().contains("чётн"));
    }

    @Test
    @DisplayName("NORMAL и EVEN — разные объекты")
    void shouldBeDifferent() {
        assertNotEquals(SortType.NORMAL, SortType.EVEN);
    }

    @Test
    @DisplayName("Описания не null и не пустые")
    void descriptionsShouldBeNonEmpty() {
        for (SortType type : SortType.values()) {
            assertNotNull(type.getDescription());
            assertFalse(type.getDescription().isEmpty());
        }
    }
}