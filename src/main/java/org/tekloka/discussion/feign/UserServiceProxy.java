package org.tekloka.discussion.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.tekloka.discussion.constants.DataConstants;
import org.tekloka.discussion.security.UserAccess;

@FeignClient(name = "user-service")
public interface UserServiceProxy {
	
	@GetMapping(path = "/user/get-user-access/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	UserAccess getUserAccess(
			@RequestHeader(value = DataConstants.FEIGN_CLIENT_ACCESS_ID, required = true) String feignClientAccessId,
			@PathVariable("userId") String userId);

}
