package makarSorting;

import makarSorting.Strategy.SmartSorter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenFieldSorter {

    public <T> void sort(
            List<T> list,
            Comparator<? super T> comparator,
            Class<T> className,
            String fieldName) {

        SmartSorter<T> smartSorter = new SmartSorter<>();

        List<T> sortList = new ArrayList<>();

        Field field;
        try {
            field = className.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(
                    "Поле " + fieldName + " не найдено", e);
        }
        field.setAccessible(true);

        Class<?> type = field.getType();

        if (isIntegerType(type)) {
            for (T obj : list) {
                Number value = getNumber(field, obj);

                if (value.longValue() % 2 == 0) {
                    sortList.add(obj);
                }
            }

            smartSorter.sort(sortList, comparator);

            int sortListRunner = 0;

            for (int i = 0; i < list.size(); i++) {
                Number value = getNumber(field, list.get(i));

                if (value.longValue() % 2 == 0) {
                    list.set(i, sortList.get(sortListRunner));
                    sortListRunner++;
                }
            }
        }
        else {
            throw new IllegalArgumentException(
                    "Выбранное вами поле не поддерживает сортировку по четности."
            );
        }
    }

    private Number  getNumber(Field field, Object obj) {
        Number value = null;
        try {
            value = (Number) field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    private boolean isIntegerType(Class<?> type) {
        return type == int.class || type == long.class || type == byte.class || type == short.class
                || type == Integer.class || type == Long.class || type == Byte.class || type == Short.class;
    }

}
