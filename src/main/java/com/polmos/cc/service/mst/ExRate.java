package com.polmos.cc.service.mst;

import com.polmos.cc.constants.OperationType;
import java.util.Objects;

/**
 *
 * @author RobicToNieMaKomu
 */
public class ExRate {

    private final String id;
    private final float ask;
    private final float bid;

    public ExRate(String id, float ask, float bid) {
        this.id = (id != null) ? id : "";
        this.ask = ask;
        this.bid = bid;
    }

    public float getAsk() {
        return ask;
    }

    public float getBid() {
        return bid;
    }

    public String getId() {
        return id;
    }
    
    public float getValue(OperationType type) {
        float output = 0;
        if (type != null) {
            switch (type) {
                case ASK:
                    output = ask;
                    break;
                case BID:
                    output = bid;
                    break;
            }
        }
        return output;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Float.floatToIntBits(this.ask);
        hash = 67 * hash + Float.floatToIntBits(this.bid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExRate other = (ExRate) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (Float.floatToIntBits(this.ask) != Float.floatToIntBits(other.ask)) {
            return false;
        }
        if (Float.floatToIntBits(this.bid) != Float.floatToIntBits(other.bid)) {
            return false;
        }
        return true;
    }
}
