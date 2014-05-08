package com.polmos.cc.service;

import com.polmos.cc.constants.BundleName;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class ResourceManager {

    private static final Logger logger = Logger.getLogger(ResourceManager.class);

    public static String getString(BundleName bundleName, String key) {
        String result = "";
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName.getName());
            result = bundle.getString(key);
        } catch (Exception e) {
            logger.error("Something went wrong during gettin value for a key:" + key + " from bundle:" + bundleName, e);
        }
        return result;
    }

    public static List<String> getAllKeys(BundleName bundleName) {
        List<String> result = new ArrayList<>();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName.getName());
            Enumeration<String> enumeration = bundle.getKeys();
            while (enumeration.hasMoreElements()) {
                result.add(enumeration.nextElement());
            }
        } catch (Exception e) {
            logger.error("Something went wrong during gettin all keys from bundle:" + bundleName, e);
        }
        return result;
    }
}
