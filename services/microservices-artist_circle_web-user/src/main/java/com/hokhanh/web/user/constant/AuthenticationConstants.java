package com.hokhanh.web.user.constant;

public final class AuthenticationConstants {
	public static final String REFRESH_TOKEN_REDIS_KEY = "token:refresh:white:";
	public static final String BLACK_ACCESS_TOKEN_REDIS_KEY = "token:access:black:";
	
	public static final String REFRESH_TOKEN_COOKIE_NAME  = "refreshToken";
	public static final String REFRESH_TOKEN_COOKIE_PATH  = "/user-service/graphql";
	public static final String SET_COOKIE_CONTEXT_KEY = "setCookie";
	public static final String REMOVE_COOKIE_CONTEXT_KEY = "removeCookie";
	public static final String AUTHORIZATION_CONTEXT_KEY = "authorization";
}
