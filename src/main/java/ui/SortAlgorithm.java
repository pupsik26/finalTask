package ui;

/**
 * Алгоритм сортировки.
 */
public enum SortAlgorithm {

    MERGE(1, "Merge Sort"),
    QUICK(2, "Quick Sort"),
    SMART(3, "Smart Sorter (автоматический выбор)");

    private final int code;
    private final String description;

    SortAlgorithm(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SortAlgorithm fromCode(int code) {
        for (SortAlgorithm algo : values()) {
            if (algo.code == code) {
                return algo;
            }
        }
        return SMART;
    }
}