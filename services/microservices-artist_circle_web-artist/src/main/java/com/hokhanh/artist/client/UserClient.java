package com.hokhanh.artist.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.hokhanh.common.graphQL.dto.GraphQLRequest;
import com.hokhanh.common.graphQL.dto.GraphQLResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClient {
	private final RestTemplate restTemplate;
	
	@Value("${user-service.base-url}")
	private String userBaseUrl;

	@Value("${spring.graphql.http.path}")
	private String graphqlPath;

	public Boolean checkUserExistsInternal(Long id) {
		String url = userBaseUrl + graphqlPath;

		String query = """
				    query($id: ID!) {
				        checkUserExistsInternal(id: $id)
				    }
				""";

		Map<String, Object> variables = Map.of("id", id);
		GraphQLRequest request = new GraphQLRequest(query, variables);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<GraphQLRequest> requestEntity = new HttpEntity<>(request, headers);

		ResponseEntity<GraphQLResponse> response = restTemplate.postForEntity(url, requestEntity,
				GraphQLResponse.class);

		// Xử lý lỗi từ GraphQL
		if (response.getBody() != null && response.getBody().errors() != null
				&& !response.getBody().errors().isEmpty()) {
			String errorMsg = response.getBody().errors().get(0).message();
			throw new RuntimeException("GraphQL Error: " + errorMsg);
		}

		Object value = response.getBody().data().get("checkUserExistsInternal");
		if (value instanceof Boolean boolValue) {
			return boolValue;
		} else {
			throw new RuntimeException("Unexpected response format");
		}
	}
}