package org.tekloka.discussion.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tekloka.discussion.constants.AppConstants;

@Component
public class DataUtil {

	private final Logger logger = LoggerFactory.getLogger(DataUtil.class);
	
	private DataUtil() {
	}
	
	public String getRequestAttributeValue(HttpServletRequest request, String key) {
		var value = "";
		try {
			value = String.valueOf(request.getAttribute(key));
		}catch (Exception e) {
			logger.error("Not able to get String value from request. {} - {}", key, e);
		}
		return value;
	}
	
	public String getRequestHeaderValue(HttpServletRequest request, String key) {
		return request.getHeader(key);
	}
	
	public String convertLocalDateTimeToString(LocalDateTime dateTime) {
		var readableDate = "";
		try{
			readableDate = dateTime.format(DateTimeFormatter.ofPattern(AppConstants.READABLE_DATE_FORMAT));
		}catch (Exception e) {
			logger.error("Not able to convert date to String. {} - {}", dateTime, e);
		}
		return readableDate;
	}

}
