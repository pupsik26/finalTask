import Model.Bus;
import Sort.SortFacade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SortEvenTest {
    private final SortFacade facade = new SortFacade();

    @Test
    void shouldSortOnlyEvenNumbers() {

        List<Bus> buses = new ArrayList<>(List.of(
                new Bus(7, "A", 100),
                new Bus(8, "B", 200),
                new Bus(3, "C", 300),
                new Bus(4, "D", 400),
                new Bus(2, "E", 500),
                new Bus(5, "F", 600)
        ));

        Bus bus7 = buses.get(0);
        Bus bus3 = buses.get(2);
        Bus bus5 = buses.get(5);

        facade.sortEven(buses, Bus.class, "number");

        assertEquals(7, buses.get(0).getNumber());
        assertEquals(2, buses.get(1).getNumber());
        assertEquals(3, buses.get(2).getNumber());
        assertEquals(4, buses.get(3).getNumber());
        assertEquals(8, buses.get(4).getNumber());
        assertEquals(5, buses.get(5).getNumber());
    }

    @Test
    void shouldSortWhenAllNumbersAreEven() {

        List<Bus> buses = new ArrayList<>(List.of(
                new Bus(8, "A", 100),
                new Bus(2, "B", 200),
                new Bus(6, "C", 300),
                new Bus(4, "D", 400)
        ));

        facade.sortEven(buses, Bus.class, "number");

        assertEquals(2, buses.get(0).getNumber());
        assertEquals(4, buses.get(1).getNumber());
        assertEquals(6, buses.get(2).getNumber());
        assertEquals(8, buses.get(3).getNumber());
    }

    @Test
    void shouldNotChangeListWhenAllNumbersAreOdd() {

        List<Bus> buses = new ArrayList<>(List.of(
                new Bus(7, "A", 100),
                new Bus(5, "B", 200),
                new Bus(3, "C", 300),
                new Bus(1, "D", 400)
        ));

        List<Bus> original = new ArrayList<>(buses);

        facade.sortEven(buses, Bus.class, "number");

        assertEquals(original, buses);
    }

    @Test
    void shouldThrowExceptionForNonIntegerField() {

        List<Bus> buses = new ArrayList<>(List.of(
                new Bus(1, "A", 100)
        ));

        assertThrows(
                IllegalArgumentException.class,
                () -> facade.sortEven(buses, Bus.class, "model")
        );
    }

    @Test
    void shouldThrowExceptionForUnknownField() {

        List<Bus> buses = new ArrayList<>(List.of(
                new Bus(1, "A", 100)
        ));

        assertThrows(
                NoSuchElementException.class,
                () -> facade.sortEven(buses, Bus.class, "unknown")
        );
    }
}
