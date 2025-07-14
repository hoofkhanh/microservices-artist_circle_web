package com.hokhanh.artist.mapper;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.model.GpsLocation;
import com.hokhanh.artist.request.gpsLocation.GpsLocationRequest;
import com.hokhanh.artist.response.gpsLocation.common.GpsLocationResponse;
import com.hokhanh.artist.response.gpsLocation.create.GpsLocationCreationResponse;
import com.hokhanh.artist.response.gpsLocation.update.GpsLocationUpdateResponse;

@Service
public class GpsLocationMapper {

	public GpsLocation toGpsLocation(GpsLocationRequest request, GpsLocationBuildContext gpsLocationBuildContext) {
		return GpsLocation.builder()
				.id(gpsLocationBuildContext.id())
				.longitude(request.longitude())
				.latitude(request.latitude())
				.build();
	}
	
	public GpsLocationCreationResponse toGpsLocationCreationResponse(GpsLocation gpsLocation, Long artistId) {
		return new GpsLocationCreationResponse(
			new GpsLocationResponse(
				gpsLocation.getId(), artistId, gpsLocation.getLongitude(), 
				gpsLocation.getLatitude(), gpsLocation.getUpdatedAt()
			)
		);
	}
	
	public GpsLocationUpdateResponse toGpsLocationUpdateResponse(GpsLocation gpsLocation, Long artistId) {
		return new GpsLocationUpdateResponse(
			new GpsLocationResponse(
				gpsLocation.getId(), artistId,gpsLocation.getLongitude(), 
				gpsLocation.getLatitude(), gpsLocation.getUpdatedAt()
			)
		);
	}
}
