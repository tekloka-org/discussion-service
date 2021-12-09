package org.tekloka.discussion.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tekloka.discussion.feign.UserServiceProxy;

@Component
public final class SecurityCache {

	@Value("${feign.client.access.id}") 
	private String feignClientAccessId;
	
	private static Map<String, UserAccess> userAccessMap = new HashMap<>();

	private final UserServiceProxy userServiceProxy;
	
	public SecurityCache(UserServiceProxy userServiceProxy) {
		this.userServiceProxy = userServiceProxy;
	}
	
	public UserAccess getUserAccess(String userId){
		return userAccessMap.get(userId);
	}
	
	public String getUserName(String userId) {
		if(userAccessMap.containsKey(userId)) {
			return userAccessMap.get(userId).getUserName();
		}else {
			var userAccess = addUserToAccessMap(userId); 
			if(null != userAccess) {
				return userAccess.getUserName();
			}
		}
		return "";
	}
	
	public Set<String> getUserPermissionSet(String userId){
		if(userAccessMap.containsKey(userId)) {
			return userAccessMap.get(userId).getPermissionKeys();
		}else {
			var userAccess = addUserToAccessMap(userId); 
			if(null != userAccess) {
				return userAccess.getPermissionKeys();
			}
			return new HashSet<>();
		}
	}
	
	public Set<String> getUserRoleSet(String userId){
		if(userAccessMap.containsKey(userId)) {
			return userAccessMap.get(userId).getRoleKeys();
		}else {
			var userAccess = addUserToAccessMap(userId); 
			if(null != userAccess) {
				return userAccess.getRoleKeys();
			}
			return new HashSet<>();
		}
	}
	
	public UserAccess addUserToAccessMap(String userId) {
		var userAccess = userServiceProxy.getUserAccess(feignClientAccessId, userId);
		if(null != userAccess) {
			userAccessMap.put(userId, userAccess);
		}
		return userAccess;
	}
	
	public boolean removeUserFromSecurityCache(String userId) {
		if(userAccessMap.containsKey(userId)) {
			userAccessMap.remove(userId);
			return true;
		}else {
			return false;
		}
	}
	
	public void clearSecurityCache() {
		userAccessMap.clear();
	}
	
	
}


