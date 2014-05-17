package com.polmos.cc.constants;

/**
 *
 * @author RobicToNieMaKomu
 */
public enum OperationType {

    BID("bid"), ASK("ask");
    private final String value;

    private OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OperationType toOperationType(String type) {
        OperationType output = null;
        if (BID.getValue().equals(type)) {
            output = BID;
        } else if (ASK.getValue().equals(type)) {
            output = ASK;
        }
        return output;
    }
}
