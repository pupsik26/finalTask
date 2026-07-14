package daryaClassStream.daryaStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentStream {
    private final String groupNumber;
    private final double averageGrade;
    private final String recordBookNumber;

    private StudentStream(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageGrade = builder.averageGrade;
        this.recordBookNumber = builder.recordBookNumber;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getGroupNumber() { return groupNumber; }
    public double getAverageGrade() { return averageGrade; }
    public String getRecordBookNumber() { return recordBookNumber; }

    @Override
    public String toString() {
        return "Student{group='" + groupNumber + "', gpa=" + averageGrade +
                ", book='" + recordBookNumber + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentStream student = (StudentStream) o;
        return Double.compare(student.averageGrade, averageGrade) == 0 &&
                Objects.equals(groupNumber, student.groupNumber) &&
                Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageGrade, recordBookNumber);
    }

    public static List<StudentStream> loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            return StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(node -> StudentStream.builder()
                            .setGroupNumber(node.get("groupNumber ").asText().trim())
                            .setAverageGrade(node.get("gpa ").asDouble())
                            .setRecordBookNumber(node.get("recordBookNumber ").asText().trim())
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    public static class Builder {
        private String groupNumber;
        private double averageGrade;
        private String recordBookNumber;

        public Builder setGroupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder setAverageGrade(double averageGrade) {
            this.averageGrade = averageGrade;
            return this;
        }

        public Builder setRecordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public StudentStream build() {
            return new StudentStream(this);
        }
    }
}