package ModelBuilderClass.Builder;

import ModelBuilderClass.ModelClass.Bus;

public class  BusBuilder extends Bus {

    public BusBuilder() {
        super();
    }

    public BusBuilder setNumber(int number) {
        this.number = number;
        return this;
    }

    public BusBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public BusBuilder setMileage(int mileage) {
        this.mileage = mileage;
        return this;
    }

    public Bus build() {
        return this;
    }
}