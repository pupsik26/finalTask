package daryaClassStream.daryaStream;

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
        System.out.println("=".repeat(80));

        String basePath = "src/main/java/dataSource/fileReader/examples/";


        System.out.println("\n1. Студенты:");
        studentStream.loadFromJson(basePath + "student.json");

        System.out.println("\n2. Автомобили");
        carStream.loadFromJsonWithFilter(basePath + "car.json", 350);

        System.out.println("\n3. Автобусы");
        busStream.loadFromJsonGroupedByModel(basePath + "bus.json");

        System.out.println("\n4. Бочки");
        barrelStream.loadFromJsonWithStatistics(basePath + "barrel.json");

        System.out.println("\n5. Пользователи");
        userStream.loadValidFromJson(basePath + "user.json");

        System.out.println("=".repeat(80));
    }
}
