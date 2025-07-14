package com.hokhanh.artist.service;


import org.springframework.stereotype.Service;

import com.hokhanh.artist.mapper.GpsLocationBuildContext;
import com.hokhanh.artist.mapper.GpsLocationMapper;
import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.GpsLocation;
import com.hokhanh.artist.repository.ArtistRepository;
import com.hokhanh.artist.repository.GpsLocationRepository;
import com.hokhanh.artist.request.gpsLocation.GpsLocationCreationRequest;
import com.hokhanh.artist.request.gpsLocation.GpsLocationRequest;
import com.hokhanh.artist.request.gpsLocation.GpsLocationUpdateRequest;
import com.hokhanh.artist.response.common.ApiResponse;
import com.hokhanh.artist.response.common.StatusType;
import com.hokhanh.artist.response.gpsLocation.create.GpsLocationCreationApiResponse;
import com.hokhanh.artist.response.gpsLocation.update.GpsLocationUpdateApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GpsLocationService {

	private final GpsLocationRepository gpsLocationRepository;
	private final ArtistRepository artistRepository;
	private final GpsLocationMapper gpsLocationMapper;
	
	public GpsLocationCreationApiResponse createGpsLocation(GpsLocationCreationRequest request, String userId) {
		Artist artist = validateArtist(userId);
		
		if(artist == null) {
			return  new GpsLocationCreationApiResponse(
				new ApiResponse(false, "Artist was not found", StatusType.ARTIST_NOT_FOUND),
				null
			);
		}
		
		GpsLocation gpsLocation = builContext(request.gpsLocation(), null);
		
		gpsLocation = saveAndSetGpsLocation(gpsLocation, artist);
		
		return new GpsLocationCreationApiResponse(
			new ApiResponse(true, "Create Gps Location successfully", null),
			gpsLocationMapper.toGpsLocationCreationResponse(gpsLocation, artist.getId())
		);
	}

	public GpsLocationUpdateApiResponse updateGpsLocation(GpsLocationUpdateRequest request, String userId) {
		Artist artist = validateArtist(userId);
		
		if(artist == null) {
			return  new GpsLocationUpdateApiResponse(
				new ApiResponse(false, "Artist was not found", StatusType.ARTIST_NOT_FOUND),
				null
			);
		}
		
		if(artist.getGpsLocation() == null) {
			return  new GpsLocationUpdateApiResponse(
				new ApiResponse(false, "GpsLocation hasn't been created yet", StatusType.ARTIST_NOT_FOUND),
				null
			);
		}
		
		GpsLocation gpsLocation = builContext(request.gpsLocation(), artist.getGpsLocation().getId());
		
		gpsLocation = saveAndSetGpsLocation(gpsLocation, artist);
		
		return new GpsLocationUpdateApiResponse(
			new ApiResponse(true, "Update Gps Location successfully", null),
			gpsLocationMapper.toGpsLocationUpdateResponse(gpsLocation, artist.getId())
		);
	}
	
	private Artist validateArtist(String userId) {
		Long userIdLong = Long.parseLong(userId);
		Artist artist = artistRepository.findByUserId(userIdLong);
		if(artist == null) {
			return null;
		}
		
		return artist;
	}
	
	private GpsLocation saveAndSetGpsLocation(GpsLocation gpsLocation, Artist artist) {
		gpsLocation = gpsLocationRepository.save(gpsLocation);
		artist.setGpsLocation(gpsLocation);
		artistRepository.save(artist);
		return gpsLocation;
	}
	
	private GpsLocation builContext(GpsLocationRequest gpsLocationRequest, Long id) {
		return gpsLocationMapper.toGpsLocation(
				gpsLocationRequest,
			new GpsLocationBuildContext(id)
		);
	}
}
