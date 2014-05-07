package com.polmos.cc.service.mst;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Pojo representing node (currency)
 * 
 * @author RobicToNieMaKomu
 */
public class Node {

    private final String currencySymbol;
    private final Set<Node> neighbors;
    
    public Node(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        this.neighbors = new HashSet<>();
    }
    
    public Node(Node node) throws IOException {
        if (node == null || node.getCurrencySymbol() == null || node.getCurrencySymbol().isEmpty()) {
            throw new IOException("Couldnt copy node");
        }
        this.currencySymbol = node.getCurrencySymbol();
        this.neighbors = new HashSet<>(node.getNeighbors());
    }
    
    public boolean addNeighbor(Node node) {
        return neighbors.add(node);
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public Set<Node> getNeighbors() {
        return new HashSet<>(neighbors);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.currencySymbol);
        hash = 97 * hash + Objects.hashCode(this.neighbors);
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
        final Node other = (Node) obj;
        if (!Objects.equals(this.currencySymbol, other.currencySymbol)) {
            return false;
        }
        if (!Objects.equals(this.neighbors, other.neighbors)) {
            return false;
        }
        return true;
    }

    @Override
    public synchronized String toString() {
        return "["+ currencySymbol + "]:" + neighbors;
    }
}
