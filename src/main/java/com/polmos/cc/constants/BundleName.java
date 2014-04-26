package com.polmos.cc.constants;

/**
 *
 * @author RobicToNieMaKomu
 */
public enum BundleName {
    CURRENCIES("currencies");
    
    private String name;
    
    private BundleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
