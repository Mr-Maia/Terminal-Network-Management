package prr.core;

public enum TerminalMode {
    BUSY ("BUSY"),
    IDLE ("ON"),
    SILENCE ("SILENCE"),
    OFF ("OFF");

    String _label;

    private TerminalMode(String label){
        _label = label;
    }

}
