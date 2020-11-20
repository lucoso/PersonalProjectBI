package br.com.lucaswagner.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JavaUtil {
	
	public static LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	    	      .atZone(ZoneId.systemDefault())
	    	      .toLocalDate();
	    	}

}
