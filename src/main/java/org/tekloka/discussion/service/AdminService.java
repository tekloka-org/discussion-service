package org.tekloka.discussion.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface AdminService {

	ResponseEntity<Object> removeUserFromSecurityCache(HttpServletRequest request, String userId);

	ResponseEntity<Object> clearSecurityCache();

}
