package org.example.SortTest;

import ModelBuilderClass.ModelClass.Bus;
import makarSorting.SortFacade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import ModelBuilderClass.Builder.BusBuilder;

class SortEvenTest {

    private final SortFacade facade = new SortFacade();

    @Test
    void shouldSortOnlyEvenNumbers() {

        List<Bus> buses = new ArrayList<>(List.of(
                new BusBuilder().setNumber(7).setModel("A").setMileage(100).build(),
                new BusBuilder().setNumber(8).setModel("B").setMileage(200).build(),
                new BusBuilder().setNumber(3).setModel("C").setMileage(300).build(),
                new BusBuilder().setNumber(4).setModel("D").setMileage(400).build(),
                new BusBuilder().setNumber(2).setModel("E").setMileage(500).build(),
                new BusBuilder().setNumber(5).setModel("F").setMileage(600).build()
        ));

        Bus bus7 = buses.get(0);
        Bus bus3 = buses.get(2);
        Bus bus5 = buses.get(5);

        facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "number");

        assertEquals(7, buses.get(0).getNumber());
        assertEquals(2, buses.get(1).getNumber());
        assertEquals(3, buses.get(2).getNumber());
        assertEquals(4, buses.get(3).getNumber());
        assertEquals(8, buses.get(4).getNumber());
        assertEquals(5, buses.get(5).getNumber());

        assertSame(bus7, buses.get(0));
        assertSame(bus3, buses.get(2));
        assertSame(bus5, buses.get(5));
    }

    @Test
    void shouldSortWhenAllNumbersAreEven() {

        List<Bus> buses = new ArrayList<>(List.of(
                new BusBuilder().setNumber(8).setModel("A").setMileage(100).build(),
                new BusBuilder().setNumber(2).setModel("B").setMileage(200).build(),
                new BusBuilder().setNumber(6).setModel("C").setMileage(300).build(),
                new BusBuilder().setNumber(4).setModel("D").setMileage(400).build()
        ));

        facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "number");

        assertEquals(2, buses.get(0).getNumber());
        assertEquals(4, buses.get(1).getNumber());
        assertEquals(6, buses.get(2).getNumber());
        assertEquals(8, buses.get(3).getNumber());
    }

    @Test
    void shouldNotChangeListWhenAllNumbersAreOdd() {

        List<Bus> buses = new ArrayList<>(List.of(
                new BusBuilder().setNumber(7).setModel("A").setMileage(100).build(),
                new BusBuilder().setNumber(5).setModel("B").setMileage(200).build(),
                new BusBuilder().setNumber(3).setModel("C").setMileage(300).build(),
                new BusBuilder().setNumber(1).setModel("D").setMileage(400).build()
        ));

        List<Bus> original = new ArrayList<>(buses);

        facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "number");

        assertEquals(original, buses);
    }

    @Test
    void shouldThrowExceptionForNonIntegerField() {

        List<Bus> buses = List.of(
                new BusBuilder().setNumber(1).setModel("A").setMileage(100).build()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "model")
        );
    }

    @Test
    void shouldThrowExceptionForUnknownField() {

        List<Bus> buses = List.of(
                new BusBuilder().setNumber(1).setModel("A").setMileage(100).build()
        );

        assertThrows(
                NoSuchElementException.class,
                () -> facade.sortEven((List<Object>) (List<?>) buses, Bus.class, "unknown")
        );
    }
}