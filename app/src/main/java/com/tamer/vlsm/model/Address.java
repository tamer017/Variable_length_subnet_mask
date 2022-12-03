package com.tamer.vlsm.model;

import java.io.Serializable;

public class Address implements Serializable {
    String name;
    int network_address;
    int broadcast_address;
    int cidr;
    int allocation;
    int required;
    int allocated;
    int first_address;
    int last_address;

    public Address(String name, int network_address, int broadcast_address, int cidr, int required, int allocated) {
        this.name = name;
        this.network_address = network_address;
        this.broadcast_address = broadcast_address;
        this.cidr = cidr;
        this.required = required;
        this.allocated = allocated;
        this.allocation =(int) (((double) required / allocated) * 100);
        if ((broadcast_address - network_address) == 1) {
            this.first_address = 0;
            this.last_address = 0;
        } else {
            this.first_address = network_address + 1;
            this.last_address = broadcast_address - 1;
        }
    }

    @Override
    public String toString() {
        return "Address [name=" + name + ", network_address=" + convertIpToQuartet(network_address)
                + ", broadcast_address="
                + convertIpToQuartet(broadcast_address) + ", cidr=" + cidr + ", allocation=" + allocation
                + ", required=" + required
                + ", allocated=" + allocated + ", first_address=" + convertIpToQuartet(first_address)
                + ", last_address=" + convertIpToQuartet(last_address)
                + "]";
    }

    public static String convertIpToQuartet(int ipAddress) {
        int octet1 = (ipAddress >> 24) & 255;
        int octet2 = (ipAddress >> 16) & 255;
        int octet3 = (ipAddress >> 8) & 255;
        int octet4 = ipAddress & 255;

        return octet1 + "." + octet2 + "." + octet3 + "." + octet4;
    }

    public static int convertQuartetToBinaryString(String ipAddress) {
        String[] ip = ipAddress.split("[./]");

        int octet1 = Integer.parseInt(ip[0]);
        int octet2 = Integer.parseInt(ip[1]);
        int octet3 = Integer.parseInt(ip[2]);
        int octet4 = Integer.parseInt(ip[3]);

        int output = octet1;
        output = (output << 8) + octet2;
        output = (output << 8) + octet3;
        output = (output << 8) + octet4;

        return output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork_address() {
        return convertIpToQuartet(network_address);
    }

    public void setNetwork_address(int network_address) {
        this.network_address = network_address;
    }

    public String getBroadcast_address() {
        return convertIpToQuartet(broadcast_address);
    }

    public void setBroadcast_address(int broadcast_address) {
        this.broadcast_address = broadcast_address;
    }

    public int getCidr() {
        return cidr;
    }

    public void setCidr(int cidr) {
        this.cidr = cidr;
    }

    public double getAllocation() {
        return allocation;
    }

    public void setAllocation(int allocation) {
        this.allocation = allocation;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public int getAllocated() {
        return allocated;
    }

    public void setAllocated(int allocated) {
        this.allocated = allocated;
    }

    public String getFirst_address() {
        return convertIpToQuartet(first_address);
    }

    public void setFirst_address(int first_address) {
        this.first_address = first_address;
    }

    public String getLast_address() {
        return convertIpToQuartet(last_address);
    }

    public void setLast_address(int last_address) {
        this.last_address = last_address;
    }

}
