package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import java.time.LocalDateTime;

import com.cmpe275.finalProject.cloudEventCenter.model.Address;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRatingData {
	String review;
	int rating;
}
