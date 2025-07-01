package com.hokhanh.web.user.response;


public record UserApiResponse(
//  main message
	boolean success,
	String message,
	UserStatusType statusType,
	UserResponse data,
	
//	optional message
	MessageOption messageOption
) {

}
