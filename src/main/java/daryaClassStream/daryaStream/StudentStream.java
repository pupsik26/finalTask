package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.Student;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class StudentStream {

    private final ObjectMapper mapper;

    public StudentStream() {
        this.mapper = new ObjectMapper();
    }

    public void loadFromJson(String filePath) {
        System.out.println("=== Загрузка студентов из JSON: " + filePath + " ===");

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseStudent)
                    .filter(Objects::nonNull)
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("✗ Ошибка чтения файла: " + e.getMessage());
        }
    }

    private Student parseStudent(JsonNode node) {
        try {
            String groupNumber = node.get("groupNumber").asText().trim();
            double gpa = node.get("gpa").asDouble();
            String recordBookNumber = node.get("recordBookNumber").asText().trim();

            return Student.builder()
                    .setGroupNumber(groupNumber)
                    .setAverageGrade(gpa)
                    .setRecordBookNumber(recordBookNumber)
                    .build();
        } catch (Exception e) {
            System.out.println("✗ Ошибка парсинга студента: " + e.getMessage());
            return null;
        }
    }
}
