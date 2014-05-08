package com.polmos.cc.service.mst;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MSTService {
    Map<String, Set<String>> generateMST(int days) throws IOException;
}
