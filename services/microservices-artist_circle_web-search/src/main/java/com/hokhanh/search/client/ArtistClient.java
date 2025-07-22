package com.hokhanh.search.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hokhanh.common.artist.response.ArtistSearchResponse;
import com.hokhanh.common.gpsLocation.response.ArtistGpsLocationResponse;
import com.hokhanh.common.graphQL.dto.GraphQLRequest;
import com.hokhanh.common.graphQL.dto.GraphQLResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArtistClient {
	private final RestTemplate restTemplate;

	@Value("${artist-service.base-url}")
	private String artistBaseUrl;

	@Value("${spring.graphql.http.path}")
	private String graphqlPath;
	
	private final ObjectMapper objectMapper;
	
	public List<ArtistSearchResponse> searchArtistsInternal(List<Long> ids) {
		String url = artistBaseUrl + graphqlPath;

		String query = """
		        query($ids: [ID!]!) {
		            searchArtistsInternal(ids: $ids) {
		                id
		                artistName
		                avatarUrl
		                residence
		                distanceInMeters
		            }
		        }
		    """;

		Map<String, Object> variables = Map.of("ids", ids);
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

		Object value = response.getBody().data().get("searchArtistsInternal");
		List<ArtistSearchResponse> result = objectMapper.convertValue(
		        value,
		        new TypeReference<List<ArtistSearchResponse>>() {}
		    );
		
		return result;
	}
	
	public ArtistGpsLocationResponse getMyGpsLocationAndArtistIdInternal(Long userId) {
		String url = artistBaseUrl + graphqlPath;

		String query = """
		        query($userId: ID!) {
				  getMyGpsLocationAndArtistIdInternal(userId: $userId) {
				    latitude
				    longitude
				    artistId
				  }
				}
		    """;

		Map<String, Object> variables = Map.of("userId", userId);
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

		Object value = response.getBody().data().get("getMyGpsLocationAndArtistIdInternal");
		ArtistGpsLocationResponse result = objectMapper.convertValue(
		    value,
		    ArtistGpsLocationResponse.class
		);

		
		return result;
	}
}
