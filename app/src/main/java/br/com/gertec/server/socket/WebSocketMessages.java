package br.com.gertec.server.socket;

public enum WebSocketMessages {
    BARCODE("sendBarCode"),
    PRINTER("sendPrinter"),
    UNDEFINED("undefined");

    private String option;

    WebSocketMessages(String userOption) {
        setEnumeration(userOption);
    }

    public static WebSocketMessages setValue(String value) {
        for (WebSocketMessages status : WebSocketMessages.values()) {
            if (status.getEnumeration().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return UNDEFINED;
    }

    private String getEnumeration() {
        return option;
    }

    private void setEnumeration(String enumeration) {
        this.option = enumeration;
    }

    public boolean equals(String value) { return getEnumeration().equalsIgnoreCase(value); }

    public String getValue() { return getEnumeration(); }

}