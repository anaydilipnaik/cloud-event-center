package com.cmpe275.finalProject.cloudEventCenter.model;

/**
* Represents a player/organization/event address
*
* @author  Adarsh Murthy
* @version 1.0
* @since   2021-12-04 
*/

import java.io.Serializable;
import javax.persistence.Embeddable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
@Component
public class Address implements Serializable{
	
	/**
	 * The street name of the Event address
	 */
	private String street;
	
	/**
	 * The apt number of the Event address
	 */
	private String number;
	
	/**
	 * The city name of the Event address
	 */
	private String city;
	
	/**
	 * The state name of the Event address
	 */
	private String state;
	
	/**
	 * The zip of the Event address
	 */
	private String zip;
	
}
