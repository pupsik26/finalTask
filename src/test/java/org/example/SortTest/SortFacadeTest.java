import Model.User;
import Sort.SortFacade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SortFacadeTest {

    @Test
    void shouldSortUsersByName() {

        List<User> users = new ArrayList<>(List.of(
                new User("ivan", "qwerty123", "ivan@mail.com"),
                new User("alex", "pass2024", "alex@gmail.com"),
                new User("maria", "maria777", "maria@yandex.ru"),
                new User("sergey", "abc123", "sergey@mail.ru"),
                new User("anna", "password", "anna@gmail.com")
        ));

        SortFacade facade = new SortFacade();

        facade.sort(users, User.class, "name");

        List<String> names = users.stream()
                .map(User::getName)
                .toList();

        assertEquals(
                List.of(
                        "alex",
                        "anna",
                        "ivan",
                        "maria",
                        "sergey"
                ),
                names
        );
    }

    @Test
    void shouldThrowWhenFieldDoesNotExist() {

        SortFacade facade = new SortFacade();

        List<User> users = new ArrayList<>();

        assertThrows(
                NoSuchElementException.class,
                () -> facade.sort(users, User.class, "abrakadabra")
        );
    }
}