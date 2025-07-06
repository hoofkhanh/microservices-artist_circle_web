package com.hokhanh.common.graphQL.dto;

import java.util.List;
import java.util.Map;


public record GraphQLResponse(Map<String, Object> data, List<GraphQLError> errors) {

}
