package org.example.SortTest;

import daryaClassStream.ModelBuilderClass.Bus;
import makarSorting.SortFacade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SortEvenTest {

    private final SortFacade facade = new SortFacade();

    @Test
    void shouldSortOnlyEvenNumbers() {

        List<Bus> buses = new ArrayList<>(List.of(
                Bus.builder().setNumber("7").setModel("A").setMileage(707).build(),
                Bus.builder().setNumber("8").setModel("B").setMileage(808).build(),
                Bus.builder().setNumber("3").setModel("C").setMileage(303).build(),
                Bus.builder().setNumber("4").setModel("D").setMileage(404).build(),
                Bus.builder().setNumber("2").setModel("E").setMileage(202).build(),
                Bus.builder().setNumber("5").setModel("F").setMileage(505).build()
        ));

        Bus bus7 = buses.get(0);
        Bus bus3 = buses.get(2);
        Bus bus5 = buses.get(5);

        facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "mileage");
        assertEquals(707, buses.get(0).getMileage());
        assertEquals(202, buses.get(1).getMileage());
        assertEquals(303, buses.get(2).getMileage());
        assertEquals(404, buses.get(3).getMileage());
        assertEquals(808, buses.get(4).getMileage());
        assertEquals(505, buses.get(5).getMileage());

        // Объекты с нечетными mileage остались на своих местах
        assertSame(bus7, buses.get(0));
        assertSame(bus3, buses.get(2));
        assertSame(bus5, buses.get(5));
    }

    @Test
    void shouldSortWhenAllNumbersAreEven() {

        List<Bus> buses = new ArrayList<>(List.of(
                Bus.builder().setNumber("8").setModel("A").setMileage(800).build(),
                Bus.builder().setNumber("2").setModel("B").setMileage(200).build(),
                Bus.builder().setNumber("6").setModel("C").setMileage(600).build(),
                Bus.builder().setNumber("4").setModel("D").setMileage(400).build()
        ));

        facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "mileage");
        assertEquals(200, buses.get(0).getMileage());
        assertEquals(400, buses.get(1).getMileage());
        assertEquals(600, buses.get(2).getMileage());
        assertEquals(800, buses.get(3).getMileage());
    }

    @Test
    void shouldNotChangeListWhenAllNumbersAreOdd() {

        List<Bus> buses = new ArrayList<>(List.of(
                Bus.builder().setNumber("7").setModel("A").setMileage(101).build(),
                Bus.builder().setNumber("5").setModel("B").setMileage(201).build(),
                Bus.builder().setNumber("3").setModel("C").setMileage(301).build(),
                Bus.builder().setNumber("1").setModel("D").setMileage(401).build()
        ));

        List<Bus> original = new ArrayList<>(buses);

        facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "mileage");
        assertEquals(original, buses);
    }

    @Test
    void shouldThrowExceptionForNonIntegerField() {

        List<Bus> buses = List.of(
                Bus.builder().setNumber("1").setModel("A").setMileage(100).build()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "model")
        );
    }

    @Test
    void shouldThrowExceptionForUnknownField() {

        List<Bus> buses = List.of(
                Bus.builder().setNumber("1").setModel("A").setMileage(100).build()
        );

        assertThrows(
                NoSuchElementException.class,
                () -> facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "unknown")
        );
    }
}