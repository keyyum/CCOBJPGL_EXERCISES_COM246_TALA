public class Car implements InternalCombustion, Electric {

    private int cylinders;
    private String battery;

    public Car(int cylinders, String battery) {
        this.cylinders = cylinders;
        this.battery = battery;
    }

    @Override
    public void gas() {
        System.out.println("Running on gas.");
    }

    @Override
    public void electric() {
        System.out.println("Running on electric power.");
    }

    @Override
    public String battery() {
        return this.battery; 
    }

    @Override
    public int Cylinders() {
        return this.cylinders;  
    }

    public int getCylinders() {
        return cylinders;
    }

    public String getBattery() {
        return battery;
    }
}