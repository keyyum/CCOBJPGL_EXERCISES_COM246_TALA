class HdmiToVgaAdapter implements VgaPort {
    private HdmiPort HdmiPort;

    public HdmiToVgaAdapter(HdmiPort HdmiPort) {
        this.HdmiPort = HdmiPort;
    }

    @Override
    public void connectWithVgaPort() {
        System.out.println("Adapting Vga Port to Hdmi Port...");
        HdmiPort.connectWithHdmiPort();  
    }
}