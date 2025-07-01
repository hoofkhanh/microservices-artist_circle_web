package com.hokhanh.web.user.response;

public record MessageOption(
	Integer expiredOtpMinutes,
	Integer expiredUserMinutes
) {

}
