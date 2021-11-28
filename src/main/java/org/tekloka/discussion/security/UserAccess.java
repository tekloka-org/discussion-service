package org.tekloka.discussion.security;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccess{
	private String userId;
	private String userName;
	private Set<String> roleKeys;
	private Set<String> permissionKeys;
	
}