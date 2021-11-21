package org.tekloka.discussion.util;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RestResponse {

	private String code;
	private Map<String, Object> data;

}
