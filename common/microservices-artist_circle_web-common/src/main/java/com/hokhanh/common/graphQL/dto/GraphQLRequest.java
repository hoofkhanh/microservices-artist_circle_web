package com.hokhanh.common.graphQL.dto;

import java.util.Map;


public record GraphQLRequest(String query, Map<String, Object> variables
) {

}
