package ui;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * Иммутабельная модель команды пользователя.
 */
public final class Command {

    public static final String ACTION_HELP = "help";
    public static final String ACTION_START = "start";
    public static final String ACTION_EXIT = "exit";
    public static final String ACTION_CLEAR = "clear";

    private final String action;
    private final OptionalInt classType;        // 1-User, 2-Student, 3-Car, 4-Bus, 5-Barrel
    private final OptionalInt dataSourceType;   // 1-manual, 2-random, 3-file
    private final OptionalInt collectionSize;
    private final OptionalInt fieldIndex;       // 1, 2, 3
    private final OptionalInt algorithmType;    // не используется (SmartSorter сам выбирает)
    private final Optional<String> filePath;

    private Command(Builder builder) {
        this.action = builder.action;
        this.classType = builder.classType;
        this.dataSourceType = builder.dataSourceType;
        this.collectionSize = builder.collectionSize;
        this.fieldIndex = builder.fieldIndex;
        this.algorithmType = builder.algorithmType;
        this.filePath = builder.filePath;
    }

    public String getAction() { return action; }
    public OptionalInt getClassType() { return classType; }
    public OptionalInt getDataSourceType() { return dataSourceType; }
    public OptionalInt getCollectionSize() { return collectionSize; }
    public OptionalInt getFieldIndex() { return fieldIndex; }
    public OptionalInt getAlgorithmType() { return algorithmType; }
    public Optional<String> getFilePath() { return filePath; }

    public boolean isHelp() { return ACTION_HELP.equalsIgnoreCase(action); }
    public boolean isExit() { return ACTION_EXIT.equalsIgnoreCase(action); }
    public boolean isStart() { return ACTION_START.equalsIgnoreCase(action); }

    public boolean hasAllRequiredParams() {
        return classType.isPresent() && dataSourceType.isPresent()
                && collectionSize.isPresent() && fieldIndex.isPresent();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder("Command{action='").append(action).append("'");
        classType.ifPresent(v -> sb.append(", class=").append(v));
        dataSourceType.ifPresent(v -> sb.append(", source=").append(v));
        collectionSize.ifPresent(v -> sb.append(", size=").append(v));
        fieldIndex.ifPresent(v -> sb.append(", field=").append(v));
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private String action = "";
        private OptionalInt classType = OptionalInt.empty();
        private OptionalInt dataSourceType = OptionalInt.empty();
        private OptionalInt collectionSize = OptionalInt.empty();
        private OptionalInt fieldIndex = OptionalInt.empty();
        private OptionalInt algorithmType = OptionalInt.empty();
        private Optional<String> filePath = Optional.empty();

        public Builder setAction(String action) { this.action = action; return this; }
        public Builder setClassType(int classType) { this.classType = OptionalInt.of(classType); return this; }
        public Builder setDataSourceType(int dataSourceType) { this.dataSourceType = OptionalInt.of(dataSourceType); return this; }
        public Builder setCollectionSize(int collectionSize) { this.collectionSize = OptionalInt.of(collectionSize); return this; }
        public Builder setFieldIndex(int fieldIndex) { this.fieldIndex = OptionalInt.of(fieldIndex); return this; }
        public Builder setAlgorithmType(int algorithmType) { this.algorithmType = OptionalInt.of(algorithmType); return this; }
        public Builder setFilePath(String filePath) { this.filePath = Optional.ofNullable(filePath); return this; }
        public Command build() { return new Command(this); }
    }
}