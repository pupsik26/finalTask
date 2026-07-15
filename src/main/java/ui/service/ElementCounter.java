package ui.service;

import java.util.List;
import java.util.concurrent.*;

/**
 * Сервис для многопоточного подсчета вхождений элемента в коллекцию.
 */
public class ElementCounter {

    /**
     * Подсчитывает количество вхождений элемента в коллекцию, используя несколько потоков.
     *
     * @param list        коллекция для поиска
     * @param targetValue строковое представление искомого элемента (сравнение через toString())
     * @param threadCount желаемое количество потоков
     * @return количество найденных вхождений
     */
    public long countOccurrences(List<?> list, String targetValue, int threadCount) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        int actualThreads = Math.min(threadCount, list.size());
        ExecutorService executor = Executors.newFixedThreadPool(actualThreads);

        int chunkSize = (int) Math.ceil((double) list.size() / actualThreads);
        long totalCount = 0;

        try {
            List<Future<Long>> futures = new java.util.ArrayList<>();

            for (int i = 0; i < list.size(); i += chunkSize) {
                int start = i;
                int end = Math.min(i + chunkSize, list.size());

                Callable<Long> task = () -> {
                    long count = 0;
                    for (int j = start; j < end; j++) {
                        if (list.get(j).toString().equals(targetValue)) {
                            count++;
                        }
                    }
                    return count;
                };

                futures.add(executor.submit(task));
            }

            for (Future<Long> future : futures) {
                totalCount += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Ошибка при многопоточном подсчете: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }

        return totalCount;
    }
}