package com.cmpe275.finalProject.cloudEventCenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsS3Config {
	
	@Value("AKIAYDHNIKLGQQXNR25M")
	private String accessKeyId;
	
	@Value("MGv1ioz11Sw8imuFr+N9RlyakdFrX8ts4ulx8TXx")
	private String secretKey;
	
	@Value("us-east-2")
	private String awsRegion;
	
	@Bean
	public AmazonS3 getClient() {
		final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);
		
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(awsRegion)
				.build();
	};
	
};