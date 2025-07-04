package com.hokhanh.artist.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.GpsLocation;
import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.model.Project;
import com.hokhanh.artist.model.Role;
import com.hokhanh.artist.request.ArtistRequest;
import com.hokhanh.artist.response.artist.ArtistResponse;
import com.hokhanh.artist.response.artist.RoleResponse;
import com.hokhanh.artist.response.common.MusicGenreResponse;
import com.hokhanh.artist.response.gpsLocation.GpsLocationResponse;
import com.hokhanh.artist.response.project.ProjectPage;

@Service
public class ArtistMapper {
	
	public Artist toArtist(ArtistRequest request, Long userId
			, List<Role> roles, List<MusicGenre> musicGenres, List<Project> projects, GpsLocation gpsLocation) {
		return Artist.builder()
				.roles(roles)
				.musicGenres(musicGenres)
				.projects(projects)
				.gpsLocation(gpsLocation)
				.userId(userId)
				.birthdate(request.birthdate())
				.gender(request.gender())
				.avatarUrl(request.avatarUrl())
				.artistName(request.artistName())
				.instagramUrl(request.instagramUrl())
				.facebookUrl(request.facebookUrl())
				.tiktokUrl(request.tiktokUrl())
				.description(request.description())
				.residence(request.residence())
				.otherRoleNames(request.otherRoleNames())
				.otherMusicGenreNames(request.otherMusicGenreNames())
				.build();
	}
	
	public ArtistResponse toArtistResponse(Artist artist, List<RoleResponse> roles, 
			List<MusicGenreResponse> musicGenres, ProjectPage projects, GpsLocationResponse gpsLocation) {
		return new ArtistResponse(
				artist.getId(), 
				roles, 
				musicGenres, 
				projects, 
				gpsLocation, 
				artist.getUserId(), 
				artist.getBirthdate(), 
				artist.isGender(), 
				artist.getAvatarUrl(), 
				artist.getArtistName(), 
				artist.getInstagramUrl(), 
				artist.getFacebookUrl(), 
				artist.getTiktokUrl(), 
				artist.getDescription(), 
				artist.getResidence(), 
				artist.getOtherRoleNames(), 
				artist.getOtherMusicGenreNames()
		);
	}
}
