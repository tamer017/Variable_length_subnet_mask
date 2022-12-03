package com.tamer.vlsm.model;

import java.io.Serializable;

public class InputSubnet implements Serializable {
    private String name;
    private int neededHosts;

    public InputSubnet(String name, int neededHosts) {
        this.name = name;
        this.neededHosts = neededHosts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNeededHosts() {
        return neededHosts;
    }

    public void setNeededHosts(int neededHosts) {
        this.neededHosts = neededHosts;
    }

}
