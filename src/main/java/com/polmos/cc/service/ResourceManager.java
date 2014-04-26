package com.polmos.cc.service;

import com.polmos.cc.constants.BundleName;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
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

    public static Set<String> getAllKeys(BundleName bundleName) {
        Set<String> result = new HashSet<>();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName.getName());
            result.addAll(bundle.keySet());
        } catch (Exception e) {
            logger.error("Something went wrong during gettin all keys from bundle:" + bundleName, e);
        }
        return result;
    }
}
