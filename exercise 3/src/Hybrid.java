public class Hybrid implements Electric, internalCombustion {
    private String name;

    public Hybrid(String name) {
        this.name = name;
    }

    public void fillGas() {
        System.out.println(name + " is refueling.");
    }

    public void chargeBattery() {
        System.out.println(name + " is charging its battery.");
    }
}