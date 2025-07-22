package com.hokhanh.search.service;


import org.springframework.stereotype.Service;

import com.hokhanh.common.gpsLocation.response.ArtistGpsLocationResponse;
import com.hokhanh.search.client.ArtistClient;
import com.hokhanh.search.model.ArtistSearchHistory;
import com.hokhanh.search.repository.ArtistSearchHistoryRepository;
import com.hokhanh.search.response.common.ApiResponse;
import com.hokhanh.search.response.common.StatusType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistSearchHistoryService {
	private final ArtistSearchHistoryRepository artistSearchHistoryRepository;
	private final ArtistClient artistClient;
	
	public ApiResponse deleteArtistSearchHistory(String id, String userId) {
		ArtistSearchHistory artistSearchHistory = artistSearchHistoryRepository.findById(id).orElse(null);
		
		if(artistSearchHistory == null) {
			return new ApiResponse(false, "Artist search history id was not found", StatusType.ARTIST_SEARCH_HISTORY_NOT_FOUND);
		}
		
		ArtistGpsLocationResponse artistGps = artistClient.getMyGpsLocationAndArtistIdInternal(Long.parseLong(userId));
		System.out.println(artistGps);
		if(!artistSearchHistory.getSearcherArtistId().equals(artistGps.artistId())) {
			return new ApiResponse(false, "You are not the searcher", StatusType.YOU_NOT_SEARCHER);
		}
		
		artistSearchHistoryRepository.deleteById(id);
		
		return new ApiResponse(true, "Deleted successfully", null);
	}

	public ApiResponse deleteAllArtistSearchHistories(String userId) {
		ArtistGpsLocationResponse artistGps = artistClient.getMyGpsLocationAndArtistIdInternal(Long.parseLong(userId));
		
		int deletedCount = artistSearchHistoryRepository.deleteBySearcherArtistId(artistGps.artistId());
		if(deletedCount == 0) {
			return new ApiResponse(false, "No records to delete", StatusType.NO_RECORDS_DELETE);
		}
		
		return new ApiResponse(true, "Deleted all successfully", null);
	}

}
