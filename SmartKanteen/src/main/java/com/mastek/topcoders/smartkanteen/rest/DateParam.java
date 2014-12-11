package com.mastek.topcoders.smartkanteen.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

public class DateParam {
	private final Date date;

	  public DateParam(String dateStr) throws WebApplicationException {
	    
	    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	      try {
			this.date = dateFormat.parse(dateStr);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST)
			        .entity("Couldn't parse date string: " + e.getMessage())
			        .build());
		}
	   
	    
	  {
	      
	    }
	  }

	  public Date getDate() {
	    return date;
	  }
	}
