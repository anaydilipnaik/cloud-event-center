package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HTTP_RESP {
	String msg;
	List<?> data;
	List<?> errors;
}
