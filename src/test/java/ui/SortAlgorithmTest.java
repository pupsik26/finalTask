package ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SortAlgorithm — алгоритмы сортировки")
class SortAlgorithmTest {

    @Nested
    @DisplayName("fromCode — парсинг кода")
    class FromCodeTests {

        @ParameterizedTest
        @CsvSource({
                "1, MERGE",
                "2, QUICK",
                "3, SMART"
        })
        @DisplayName("Корректно парсит валидные коды 1-3")
        void shouldParseValidCodes(int code, SortAlgorithm expected) {
            assertEquals(expected, SortAlgorithm.fromCode(code));
        }

        @Test
        @DisplayName("Для неизвестного кода возвращает SMART (по умолчанию)")
        void shouldReturnSmartForInvalidCodes() {
            assertEquals(SortAlgorithm.SMART, SortAlgorithm.fromCode(99));
            assertEquals(SortAlgorithm.SMART, SortAlgorithm.fromCode(0));
            assertEquals(SortAlgorithm.SMART, SortAlgorithm.fromCode(-1));
        }
    }

    @Nested
    @DisplayName("Коды алгоритмов")
    class CodeTests {

        @Test
        @DisplayName("MERGE имеет код 1")
        void mergeShouldHaveCode1() {
            assertEquals(1, SortAlgorithm.MERGE.getCode());
        }

        @Test
        @DisplayName("QUICK имеет код 2")
        void quickShouldHaveCode2() {
            assertEquals(2, SortAlgorithm.QUICK.getCode());
        }

        @Test
        @DisplayName("SMART имеет код 3")
        void smartShouldHaveCode3() {
            assertEquals(3, SortAlgorithm.SMART.getCode());
        }
    }

    @Test
    @DisplayName("Содержит ровно 3 значения")
    void shouldHaveThreeValues() {
        assertEquals(3, SortAlgorithm.values().length);
    }

    @Test
    @DisplayName("Все описания непустые")
    void allDescriptionsShouldBeNonEmpty() {
        for (SortAlgorithm algo : SortAlgorithm.values()) {
            assertNotNull(algo.getDescription());
            assertFalse(algo.getDescription().isEmpty());
        }
    }
}