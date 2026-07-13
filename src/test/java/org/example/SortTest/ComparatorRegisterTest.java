package org.example.SortTest;
import daryaClassStream.ModelBuilderClass.User;
import makarSorting.ComparatorRegister;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ComparatorRegisterTest {

    @Test
    void shouldReturnNoSuchElementExceptionForField() {
        ComparatorRegister register = new ComparatorRegister();

        assertThrows(
                NoSuchElementException.class,
                () -> register.findComparator(User.class, "abrakadabra")
        );
    }

    @Test
    void shouldReturnNoSuchElementExceptionForClass() {
        ComparatorRegister register = new ComparatorRegister();

        assertThrows(
                NoSuchElementException.class,
                () -> register.findComparator(String.class, "name")
        );
    }

    @Test
    void shouldReturnComparatorForName() {
        ComparatorRegister register = new ComparatorRegister();

        Comparator<User> comparator =
                register.findComparator(User.class, "name");

        User alex = User.builder()
                .setName("alex")
                .setPassword("123")
                .setEmail("a@mail.com")
                .build();

        User ivan = User.builder()
                .setName("ivan")
                .setPassword("456")
                .setEmail("i@mail.com")
                .build();

        assertTrue(comparator.compare(alex, ivan) < 0);
    }
}