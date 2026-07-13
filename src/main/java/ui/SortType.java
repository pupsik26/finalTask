package ui;

/**
 * Тип сортировки.
 */
public enum SortType {

    NORMAL("Обычная сортировка"),
    EVEN("Сортировка только чётных значений");

    private final String description;

    SortType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}