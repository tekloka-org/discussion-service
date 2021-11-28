package org.tekloka.discussion.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tekloka.discussion.constants.ResponseConstants;
import org.tekloka.discussion.security.SecurityCache;
import org.tekloka.discussion.service.AdminService;
import org.tekloka.discussion.util.ResponseUtil;

@Service
public class AdminServiceImpl implements AdminService{

	private final SecurityCache securityCache;
	private final ResponseUtil responseUtil;
	
	public AdminServiceImpl(SecurityCache securityCache, ResponseUtil responseUtil) {
		this.securityCache = securityCache;
		this.responseUtil = responseUtil;
	}
	
	@Override
	public ResponseEntity<Object> removeUserFromSecurityCache(HttpServletRequest request, String userId) {
		Map<String, Object> dataMap = new HashMap<>();
		securityCache.removeUserFromSecurityCache(userId);
		return responseUtil.generateResponse(dataMap, ResponseConstants.USER_REMOVED_FROM_SECURITY_CACHE);
	}
	
	@Override
	public ResponseEntity<Object> clearSecurityCache() {
		Map<String, Object> dataMap = new HashMap<>();
		securityCache.clearSecurityCache();
		return responseUtil.generateResponse(dataMap, ResponseConstants.SECURITY_CACHE_CLEARED);
	}

}
