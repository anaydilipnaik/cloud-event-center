package com.cmpe275.finalProject.cloudEventCenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cmpe275.finalProject.cloudEventCenter.model.MimicClockTime;

@SpringBootApplication
@EnableScheduling
public class CloudEventCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudEventCenterApplication.class, args);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		MimicClockTime.getInstance(dateFormat.format(date));
	}

}
