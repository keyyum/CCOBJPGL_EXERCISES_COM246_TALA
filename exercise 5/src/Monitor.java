
class Monitor {
    public void display(VgaPort adapter) {
        System.out.println("Monitor expecting Vga...");
        adapter.connectWithVgaPort();
        System.out.println("Connected to Adapter.");
    }
}