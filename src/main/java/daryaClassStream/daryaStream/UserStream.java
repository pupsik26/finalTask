package daryaClassStream.daryaStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserStream {
    private final String name;
    private final String password;
    private final String email;

    private UserStream(Builder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStream user = (UserStream) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, email);
    }

    public static List<UserStream> loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            return StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(node -> UserStream.builder()
                            .setName(node.get("name ").asText().trim())
                            .setPassword(node.get("password ").asText().trim())
                            .setEmail(node.get("email ").asText().trim())
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    public static class Builder {
        private String name;
        private String password;
        private String email;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserStream build() {
            return new UserStream(this);
        }
    }
}