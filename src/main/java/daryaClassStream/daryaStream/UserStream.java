package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserStream {
    private final ObjectMapper mapper = new ObjectMapper();

    public void loadFromJson(String filePath) {
        System.out.println("Загрузка пользователей");
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseUser)
                    .collect(Collectors.toList())
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private User parseUser(JsonNode node) {
        return User.builder()
                .setName(node.get("name ").asText().trim())
                .setPassword(node.get("password ").asText().trim())
                .setEmail(node.get("email ").asText().trim())
                .build();
    }
}