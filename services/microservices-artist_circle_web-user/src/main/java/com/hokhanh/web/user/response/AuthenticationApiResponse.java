package com.hokhanh.web.user.response;

public record AuthenticationApiResponse(
	boolean success,
	String message,
	AuthenticationStatusType statusType,
	String accessToken
) {

}
