package org.tekloka.discussion.aop;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.tekloka.discussion.constants.ResponseConstants;
import org.tekloka.discussion.exception.UnauthorizedException;
import org.tekloka.discussion.util.ResponseUtil;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private final ResponseUtil responseUtil;
	
	public GlobalExceptionHandler(ResponseUtil responseUtil) {
		this.responseUtil = responseUtil;
	}

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleExceptions(UnauthorizedException exception, WebRequest webRequest) {
    	Map<String, Object> dataMap = new HashMap<>();
        return responseUtil.generateResponse(dataMap, ResponseConstants.FORBIDDEN);
    }
    
}