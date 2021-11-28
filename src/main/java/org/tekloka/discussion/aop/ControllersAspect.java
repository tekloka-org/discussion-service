package org.tekloka.discussion.aop;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tekloka.discussion.constants.DataConstants;
import org.tekloka.discussion.constants.RoleConstants;
import org.tekloka.discussion.exception.UnauthorizedException;
import org.tekloka.discussion.security.AccessPermissions;
import org.tekloka.discussion.security.SecurityCache;
import org.tekloka.discussion.util.DataUtil;

@Aspect
@Component
public class ControllersAspect {
	
	private final SecurityCache securityCache;
	private final DataUtil dataUtil;
	
	@Value("${feign.client.access.id}") 
	private String feignClientAccessId;
	
	public ControllersAspect(SecurityCache securityCache, DataUtil dataUtil) {
		this.securityCache = securityCache;
		this.dataUtil = dataUtil;
	}

	@Before("@annotation(org.tekloka.article.security.AccessPermissions)")
	public void verifyAccessPermissions(JoinPoint joinPoint){
		var hasAccess = false;
		String accessPermission = ((MethodSignature) joinPoint.getSignature()).getMethod()
				.getAnnotation(AccessPermissions.class).value();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				 .getRequest();
		var loggedInUserId = dataUtil.getRequestHeaderValue(request, DataConstants.LOGGED_IN_USER_ID);
		Set<String> userRolSet = securityCache.getUserRoleSet(loggedInUserId);
		Set<String> userPermissionSet = securityCache.getUserPermissionSet(loggedInUserId);
		if((null != userRolSet && userRolSet.contains(RoleConstants.SUPER_ADMIN)) ||
				(null != userPermissionSet && userPermissionSet.contains(accessPermission))) {
			hasAccess = true;
		}
		
		if(!hasAccess) {
			 throw new UnauthorizedException();
		}
	}
	
	@Before("@annotation(org.tekloka.article.security.AllowFeignClient)")
	public void verifyFeignClient(JoinPoint joinPoint) {
		var hasAccess = false;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		var requestFeignClientAccessId = dataUtil.getRequestHeaderValue(request,
				DataConstants.FEIGN_CLIENT_ACCESS_ID);
		Set<String> userRolSet = securityCache.getUserRoleSet(dataUtil.getRequestAttributeValue(request, DataConstants.LOGGED_IN_USER_ID));
		
		if(feignClientAccessId.equals(requestFeignClientAccessId) || (null != userRolSet && userRolSet.contains(RoleConstants.SUPER_ADMIN))) {
			hasAccess = true;
		}
		if (!hasAccess) {
			throw new UnauthorizedException();
		}
	}
	
}