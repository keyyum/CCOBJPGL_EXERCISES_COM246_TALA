public class hybridVehicle extends Hybrid {
    String carName;

    public hybridVehicle(String carName) {
        super(carName); // Call the constructor of Hybrid
        this.carName = carName;
    }

    @Override
    public void chargeBattery() {
        super.chargeBattery(); // Correctly calls the method from Hybrid
    }

    @Override
    public void fillGas() {
        super.fillGas(); // Correctly calls the method from Hybrid
    }

    public void displayInfo() {
        System.out.println(carName);
    }

    public String getName() {
        return carName;
    }
}
