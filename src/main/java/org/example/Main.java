package org.example;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorder;
import software.amazon.awssdk.services.s3.S3Client;



public class Main {

    private static final AWSXRayRecorder xray = AWSXRay.getGlobalRecorder();
    private static final String SEGMENT_NAME = "XRay Console Program";
    private static final String SUB_SEGMENT_NAME = "List buckets";
    public static void main(String[] args) {
        System.out.println("Starting S3 SDK demo");

        var segment = xray.beginSegment(SEGMENT_NAME);

        try{
            xray.beginSubsegment(SUB_SEGMENT_NAME);

            listBuckets();

            xray.endSubsegment();
        }catch (Exception e) {
            segment.addException(e);
        }finally {
            xray.endSegment();
        }



    }

    private static void listBuckets(){
        var s3Client = S3Client.builder().build();

        var response = s3Client.listBuckets();

        response.buckets().forEach(bucket -> System.out.println(bucket.name()));

    }

}