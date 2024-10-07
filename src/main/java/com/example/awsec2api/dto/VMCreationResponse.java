package com.example.awsec2api.dto;
// When a new EC2 instance is created, you need to return the instance ID to the client. 
// This DTO (VMCreationResponse) encapsulates the instance ID in a structured way.
// Instead of just sending the raw string (instance ID), 
// we wrap it inside an object, which makes the API response 
// more structured and easier to extend in the future.
// instead of returning a raw string for
//  the instance ID or a complex EC2 object, 
// VMCreationResponse wraps the instance ID into 
// an easy-to-understand format.
public class VMCreationResponse {
    private String instanceId;

    public VMCreationResponse(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}