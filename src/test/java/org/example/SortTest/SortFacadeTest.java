package org.example.SortTest;

import daryaClassStream.ModelBuilderClass.User;
import makarSorting.SortFacade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SortFacadeTest {

    @Test
    void shouldSortUsersByName() {

        List<User> users = new ArrayList<>(List.of(
                User.builder()
                        .setName("ivan")
                        .setPassword("qwerty123")
                        .setEmail("ivan@mail.com")
                        .build(),

                User.builder()
                        .setName("alex")
                        .setPassword("pass2024")
                        .setEmail("alex@gmail.com")
                        .build(),

                User.builder()
                        .setName("maria")
                        .setPassword("maria777")
                        .setEmail("maria@yandex.ru")
                        .build(),

                User.builder()
                        .setName("sergey")
                        .setPassword("abc123")
                        .setEmail("sergey@mail.ru")
                        .build(),

                User.builder()
                        .setName("anna")
                        .setPassword("password")
                        .setEmail("anna@gmail.com")
                        .build()
        ));

        SortFacade facade = new SortFacade();

        facade.sort((List<Object>) (List<?>) users, User.class, "name");

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
                () -> facade.sort((List<Object>) (List<?>) users, User.class, "abrakadabra")
        );
    }
}