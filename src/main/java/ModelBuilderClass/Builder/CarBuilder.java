package ModelBuilderClass.Builder;

import ModelBuilderClass.ModelClass.Car;

public class CarBuilder extends Car {

    public CarBuilder() {
        super();
    }

    public CarBuilder setPower(int power) {
        this.power = power;
        return this;
    }

    public CarBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public CarBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    public Car build() {
        return new Car(this.power, this.model, this.year);
    }
}