package com.hokhanh.web.user.jwt;

import java.util.HashMap;
import java.util.Map;

public record ClaimsData(
	Long userId,
	String email,
    String roleName
) {
	public Map<String, Object> toMap() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roleName", roleName);
        return claims;
    }
}
