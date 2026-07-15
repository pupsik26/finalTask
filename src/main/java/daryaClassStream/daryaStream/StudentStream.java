package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.Student;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentStream {
    private final ObjectMapper mapper = new ObjectMapper();

    public void loadFromJson(String filePath) {
        System.out.println("Загрузка студентов из JSON");
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseStudent)
                    .collect(Collectors.toList())
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Student parseStudent(JsonNode node) {
        return Student.builder()
                .setGroupNumber(node.get("groupNumber ").asText().trim())
                .setAverageGrade(node.get("gpa ").asDouble())
                .setRecordBookNumber(node.get("recordBookNumber ").asText().trim())
                .build();
    }
}