package com.cmpe275.finalProject.cloudEventCenter.service;

import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class AwsS3Service {
	
	private AmazonS3 amazonS3;
	
    @Autowired
    public void AwsS3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
	
    public String generatePreSignedURL(
    		String filePath,
    		String bucketName,
    		HttpMethod httpMethod
    ) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new java.util.Date());
    	calendar.add(Calendar.DATE, 1);
    	return amazonS3.generatePresignedUrl(
    			bucketName, 
    			filePath, 
    			calendar.getTime(), 
    			httpMethod
    	).toString();
    };
	
    public void upload(
    		String path,
    		String fileName,
    		InputStream inputStream,
    		Optional<Map<String, String>> optionalMetaData
    ) {
    	ObjectMetadata objMetaData = new ObjectMetadata();
    	optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objMetaData::addUserMetadata);
            }
        });
    	try {
    		amazonS3.putObject(path, fileName, inputStream, objMetaData);
    	} catch(AmazonServiceException ex) {
    		System.out.println("Failed to upload image");
    		throw ex;
    	}
    };
	
	
};