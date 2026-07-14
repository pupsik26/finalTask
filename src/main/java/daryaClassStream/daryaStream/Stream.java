/*package daryaClassStream.daryaStream;

public class Stream {

    private final StudentStream studentStream;
    private final CarStream carStream;
    private final BusStream busStream;
    private final BarrelStream barrelStream;
    private final UserStream userStream;

    public Stream() {
        this.studentStream = new StudentStream();
        this.carStream = new CarStream();
        this.busStream = new BusStream();
        this.barrelStream = new BarrelStream();
        this.userStream = new UserStream();
    }

    public void demonstrateAllProcessors() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ДЕМОНСТРАЦИЯ 5 КЛАССОВ ДЛЯ ОБРАБОТКИ JSON ЧЕРЕЗ СТРИМЫ");
        System.out.println("=".repeat(80));

        String basePath = "src/main/dataSource/fileReader/examples/";

        // 1. Студенты
        System.out.println("\n1. СТУДЕНТЫ:");
        studentStream.loadFromJson(basePath + "student.json");

        // 2. Автомобили (с фильтрацией по мощности >= 350)
        System.out.println("\n2. АВТОМОБИЛИ (мощность >= 350):");
        carStream.loadFromJsonWithFilter(basePath + "car.json", 350);

        // 3. Автобусы (с группировкой по модели)
        System.out.println("\n3. АВТОБУСЫ (по моделям):");
        busStream.loadFromJsonGroupedByModel(basePath + "bus.json");

        // 4. Бочки (со статистикой)
        System.out.println("\n4. БОЧКИ:");
        barrelStream.loadFromJsonWithStatistics(basePath + "barrel.json");

        // 5. Пользователи
        System.out.println("\n5. ПОЛЬЗОВАТЕЛИ:");
        userStream.loadFromJson(basePath + "user.json");

        System.out.println("=".repeat(80));
    }
}*/