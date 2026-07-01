package ivans.task.common.classes;

public class Bus {
    private int number;
    private String model;
    private int mileage;

    public Bus(int number, String model, int mileage) {
        this.number = number;
        this.model = model;
        this.mileage = mileage;
    }

    public int getNumber() { return number; }
    public String getModel() { return model; }
    public int getMileage() { return mileage; }

    public void setNumber(int number) { this.number = number; }
    public void setModel(String model) { this.model = model; }
    public void setMileage(int mileage) { this.mileage = mileage; }

    @Override
    public String toString() {
        return "Bus{number=" + number + ", model='" + model + "', mileage=" + mileage + "}";
    }
}
