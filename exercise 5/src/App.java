//App.java
public class App {
    public static void main(String[] args) {
        HdmiPort oldPort = new HdmiPort();

        VgaPort adapter = new HdmiToVgaAdapter(oldPort);


        Monitor monitor = new Monitor();

        monitor.display(adapter);
    }
}