package com.hokhanh.gateway.user;

import java.util.List;
import java.util.Map;

public record GraphQLError(String message, List<Object> path, Map<String, Object> extensions) {

}
