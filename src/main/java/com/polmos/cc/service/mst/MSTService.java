package com.polmos.cc.service.mst;

import com.polmos.cc.constants.OperationType;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MSTService {
    Map<String, Set<String>> generateMST(int days, OperationType type) throws IOException;
}
