package com.cmpe275.finalProject.cloudEventCenter.model;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class MimicClockTime extends Clock {
	
	private static MimicClockTime mimicClockTimeInstance;
	
	  
	  
	  private static void log(Object msg){
	    System.out.println(Objects.toString(msg));
	  }
	  
	
	  
	  private MimicClockTime(LocalDateTime t0, ZoneOffset zoneOffset){
	    this.t0Instant = t0.toInstant(zoneOffset);
		this.zoneOffset = zoneOffset;
	    this.t0LocalDateTime = t0;
		this.whenObjectCreatedInstant = Instant.now();
	  }
	  
	  public static MimicClockTime getInstance(String date) {
		  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",Locale.US);
		  String currentTime = sdf.format(new Date());
		  //if (mimicClockTimeInstance == null)

		  mimicClockTimeInstance = new MimicClockTime(
					 LocalDateTime.parse(date+'T'+currentTime), ZoneOffset.of("-07:00")
					);

			return mimicClockTimeInstance;

	}
	  
	 public static MimicClockTime getCurrentTime() {
		 return mimicClockTimeInstance;
	 }

	  
	  @Override public ZoneId getZone() {
	    return zoneOffset;
	  }
	  
	  /** The caller needs to actually pass a ZoneOffset object here. */
	  @Override public Clock withZone(ZoneId zone) {
	    return new MimicClockTime(t0LocalDateTime, (ZoneOffset)zone);
	  }
	  
	  @Override public Instant instant() {
	    return nextInstant();
	  }
	  
	  //PRIVATE
	  
	  /** t0 is the moment this clock object was created in Java-land. */
	  private final Instant t0Instant;
	  private final LocalDateTime t0LocalDateTime;
	  
	  private final ZoneOffset zoneOffset;
	  private final Instant whenObjectCreatedInstant;
	  
	  /** 
	   Figure out how much time has elapsed between the moment this 
	   object was created, and the moment when this method is being called.
	   Then, apply that diff to t0. 
	  */
	  private Instant nextInstant() {
	    Instant now = Instant.now();
	    long diff = now.toEpochMilli() - whenObjectCreatedInstant.toEpochMilli();
	    return t0Instant.plusMillis(diff);
	  }
	} 
