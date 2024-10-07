package com.example.awsec2api.dto;

import java.util.List;

public class VMListResponse 
// It helps us bundle the list of EC2 instances (VMInfo objects) into a single, 
// structured response. This makes it easier to send data back
//  to the client in a neat format.
 {
    private List<VMInfo> instances;

    public VMListResponse(List<VMInfo> instances) {
        this.instances = instances;
    }

    public List<VMInfo> getInstances() {
        return instances;
    }

    public void setInstances(List<VMInfo> instances) {
        this.instances = instances;
    }
}