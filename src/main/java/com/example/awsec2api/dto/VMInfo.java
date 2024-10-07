package com.example.awsec2api.dto;
// The purpose of VMInfo is to represent a single EC2 instance with two key pieces of information:
// instanceId: The unique ID of the EC2 instance.
// state: The current state of the instance (e.g., running, stopped).
public class VMInfo {
    private String instanceId;
    private String state;

    public VMInfo(String instanceId, String state) {
        this.instanceId = instanceId;
        this.state = state;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}