package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserStream {

    private final ObjectMapper mapper;

    public UserStream() {
        this.mapper = new ObjectMapper();
    }

    public void loadValidFromJson(String filePath) {
        System.out.println("Загрузка валидных пользователей");

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            var users = StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseUser)
                    .filter(Objects::nonNull)
                    .filter(this::isValidUser)
                    .map(user -> User.builder()
                            .setName(user.getName().trim().toUpperCase())
                            .setPassword(user.getPassword())
                            .setEmail(user.getEmail().trim())
                            .build())
                    .collect(Collectors.toList());

            users.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private User parseUser(JsonNode node) {
        try {
            String name = node.get("name").asText().trim();
            String password = node.get("password").asText().trim();
            String email = node.get("email").asText().trim();

            return User.builder()
                    .setName(name)
                    .setPassword(password)
                    .setEmail(email)
                    .build();
        } catch (Exception e) {
            System.out.println("Ошибка парсинга пользователя: " + e.getMessage());
            return null;
        }
    }

    private boolean isValidUser(User user) {
        return user.getName() != null && !user.getName().isEmpty() &&
                user.getPassword() != null && user.getPassword().length() >= 4 &&
                user.getEmail() != null && user.getEmail().contains("@");
    }
}
