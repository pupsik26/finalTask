package ui;

import java.util.Optional;
import java.util.OptionalInt;

public final class Command {

    private final CommandAction action;
    private final OptionalInt classType;
    private final OptionalInt dataSourceType;
    private final OptionalInt collectionSize;
    private final OptionalInt fieldIndex;
    private final Optional<SortType> sortType;
    private final OptionalInt algorithmCode;
    private final Optional<String> filePath;
    private final Optional<String> outputPath;
    private final OptionalInt threadCount;
    private final Optional<String> searchValue;

    private Command(Builder builder) {
        this.action = builder.action;
        this.classType = builder.classType;
        this.dataSourceType = builder.dataSourceType;
        this.collectionSize = builder.collectionSize;
        this.fieldIndex = builder.fieldIndex;
        this.sortType = builder.sortType;
        this.algorithmCode = builder.algorithmCode;
        this.filePath = builder.filePath;
        this.outputPath = builder.outputPath;
        this.threadCount = builder.threadCount;
        this.searchValue = builder.searchValue;
    }

    public CommandAction getAction() { return action; }
    public OptionalInt getClassType() { return classType; }
    public OptionalInt getDataSourceType() { return dataSourceType; }
    public OptionalInt getCollectionSize() { return collectionSize; }
    public OptionalInt getFieldIndex() { return fieldIndex; }
    public Optional<SortType> getSortType() { return sortType; }
    public OptionalInt getAlgorithmCode() { return algorithmCode; }
    public Optional<String> getFilePath() { return filePath; }
    public Optional<String> getOutputPath() { return outputPath; }
    public OptionalInt getThreadCount() { return threadCount; }
    public Optional<String> getSearchValue() { return searchValue; }

    public boolean isHelp() { return action == CommandAction.HELP; }
    public boolean isStart() { return action == CommandAction.START; }
    public boolean isExit() { return action == CommandAction.EXIT; }
    public boolean isClear() { return action == CommandAction.CLEAR; }
    public boolean isUnknown() { return action == CommandAction.UNKNOWN; }

    public boolean hasAllRequiredParams() {
        return classType.isPresent()
                && dataSourceType.isPresent()
                && collectionSize.isPresent()
                && fieldIndex.isPresent();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder("Command{action=").append(action.getCommand());
        classType.ifPresent(v -> sb.append(", class=").append(v));
        dataSourceType.ifPresent(v -> sb.append(", source=").append(v));
        collectionSize.ifPresent(v -> sb.append(", size=").append(v));
        fieldIndex.ifPresent(v -> sb.append(", field=").append(v));
        sortType.ifPresent(v -> sb.append(", sortType=").append(v));
        algorithmCode.ifPresent(v -> sb.append(", algo=").append(v));
        filePath.ifPresent(v -> sb.append(", path='").append(v).append("'"));
        outputPath.ifPresent(v -> sb.append(", output='").append(v).append("'"));
        threadCount.ifPresent(v -> sb.append(", threads=").append(v));
        searchValue.ifPresent(v -> sb.append(", search='").append(v).append("'"));
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private CommandAction action = CommandAction.UNKNOWN;
        private OptionalInt classType = OptionalInt.empty();
        private OptionalInt dataSourceType = OptionalInt.empty();
        private OptionalInt collectionSize = OptionalInt.empty();
        private OptionalInt fieldIndex = OptionalInt.empty();
        private Optional<SortType> sortType = Optional.empty();
        private OptionalInt algorithmCode = OptionalInt.empty();
        private Optional<String> filePath = Optional.empty();
        private Optional<String> outputPath = Optional.empty();
        private OptionalInt threadCount = OptionalInt.empty();
        private Optional<String> searchValue = Optional.empty();

        public Builder setAction(CommandAction action) { this.action = action; return this; }
        public Builder setClassType(int classType) { this.classType = OptionalInt.of(classType); return this; }
        public Builder setDataSourceType(int dataSourceType) { this.dataSourceType = OptionalInt.of(dataSourceType); return this; }
        public Builder setCollectionSize(int collectionSize) { this.collectionSize = OptionalInt.of(collectionSize); return this; }
        public Builder setFieldIndex(int fieldIndex) { this.fieldIndex = OptionalInt.of(fieldIndex); return this; }
        public Builder setSortType(SortType sortType) { this.sortType = Optional.of(sortType); return this; }
        public Builder setAlgorithmCode(int algorithmCode) { this.algorithmCode = OptionalInt.of(algorithmCode); return this; }
        public Builder setFilePath(String filePath) { this.filePath = Optional.ofNullable(filePath); return this; }
        public Builder setOutputPath(String outputPath) { this.outputPath = Optional.ofNullable(outputPath); return this; }
        public Builder setThreadCount(int threadCount) { this.threadCount = OptionalInt.of(threadCount); return this; }
        public Builder setSearchValue(String searchValue) { this.searchValue = Optional.ofNullable(searchValue); return this; }
        public Command build() { return new Command(this); }
    }
}