public class App {
    public static void main(String[] args) {

        hybridVehicle car1 = new hybridSedan("Hybrid Sedan");
        hybridVehicle car2 = new hybridPickup("Hybrid Pickup");
        carWash wash = new carWash();

        car1.displayInfo();
        car1.fillGas();

        System.out.println();

        car2.chargeBattery();
        car2.displayInfo();

        System.out.println();

        wash.wash(car1);
        wash.wash(car2);
        
    }
}
