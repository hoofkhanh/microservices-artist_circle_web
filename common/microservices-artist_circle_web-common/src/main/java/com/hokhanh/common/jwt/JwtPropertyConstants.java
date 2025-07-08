package com.hokhanh.common.jwt;


import com.hokhanh.common.util.EnvLoaderUtil;


public final class JwtPropertyConstants {
	public static final String SECRET_KEY = EnvLoaderUtil.getJwtSecretKey();

	public static final Long JWT_EXPIRATION = EnvLoaderUtil.getJwtExpiration();;

	public static final Long REFRESH_EXPIRATION = EnvLoaderUtil.getJwtRefreshTokenExpiration();
}
