package com.example.awsec2api.service;

import com.example.awsec2api.dto.VMInfo;
import com.example.awsec2api.dto.VMListResponse;
import com.example.awsec2api.exception.EC2Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EC2Service {
    // The Logger is used to log important events, such as instance creation,
    //  retrieval, and deletion, and to capture errors.
    private static final Logger logger = LoggerFactory.getLogger(EC2Service.class);

    private final Ec2Client ec2Client;

    @Autowired
    // It is configured in the AwsConfig 
    // class to provide credentials, region, and other 
    // configurations.
    public EC2Service(Ec2Client ec2Client) {
        this.ec2Client = ec2Client;
    }
 //creating new ec2 instance//
    public String createVM() {
        try {
            RunInstancesRequest runRequest = RunInstancesRequest.builder()
                    .imageId("ami-0ebfd941bbafe70c6")
                    .instanceType(InstanceType.T2_MICRO)
                    .maxCount(1)
                    .minCount(1)
                    .build(); //build instance with given specifications/

            RunInstancesResponse response = ec2Client.runInstances(runRequest);//too create ec2 instance
            String instanceId = response.instances().get(0).instanceId();
            logger.info("Created new EC2 instance with ID: {}", instanceId);
            return instanceId;
        } catch (Ec2Exception e) {
            logger.error("Error creating EC2 instance: {}", e.getMessage());
            throw new EC2Exception("Failed to create EC2 instance", e);
        }
    }

    public VMListResponse getActiveVMs() //retrives a listof active vms
     {
        try {
            DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();
            DescribeInstancesResponse response = ec2Client.describeInstances(request);

            List<VMInfo> activeInstances = response.reservations().stream()
                    .flatMap(reservation -> reservation.instances().stream())
                    .filter(instance -> instance.state().name() == InstanceStateName.RUNNING)
                    .map(instance -> new VMInfo(instance.instanceId(), instance.state().name().toString()))
                    .collect(Collectors.toList());

            logger.info("Retrieved {} active EC2 instances", activeInstances.size());
            return new VMListResponse(activeInstances);
        } catch (Ec2Exception e) {
            logger.error("Error retrieving active EC2 instances: {}", e.getMessage());
            throw new EC2Exception("Failed to retrieve active EC2 instances", e);
        }
    }
    public String deleteVM(String instanceId) {
        try {
            TerminateInstancesRequest terminateRequest = TerminateInstancesRequest.builder()
                    .instanceIds(instanceId)
                    .build();

            TerminateInstancesResponse terminateResponse = ec2Client.terminateInstances(terminateRequest);
            
            String terminatedInstanceId = terminateResponse.terminatingInstances().get(0).instanceId();
            logger.info("Terminated EC2 instance with ID: {}", terminatedInstanceId);
            
            return terminatedInstanceId;
        } catch (Ec2Exception e) {
            logger.error("Error deleting EC2 instance: {}", e.getMessage());
            throw new EC2Exception("Failed to terminate EC2 instance with ID: " + instanceId, e);
        }
    }
}
