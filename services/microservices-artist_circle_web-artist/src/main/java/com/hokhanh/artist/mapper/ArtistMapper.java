package com.hokhanh.artist.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.GpsLocation;
import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.model.Project;
import com.hokhanh.artist.model.Role;
import com.hokhanh.artist.request.artist.ArtistRequest;
import com.hokhanh.artist.response.artist.common.ArtistResponse;
import com.hokhanh.artist.response.artist.common.RoleResponse;
import com.hokhanh.artist.response.artist.profileUpdate.ArtistProfileUpdateResponse;
import com.hokhanh.artist.response.artist.registration.ArtistRegistrationResponse;
import com.hokhanh.artist.response.common.MusicGenreResponse;


@Service
public class ArtistMapper {
	
	public Artist toArtist(ArtistRequest request, ArtistBuildContext artistBuildContext
			, List<Role> roles, List<MusicGenre> musicGenres, List<Project> projects, GpsLocation gpsLocation) {
		return Artist.builder()
				.id(artistBuildContext.id())
				.roles(roles)
				.musicGenres(musicGenres)
				.projects(projects)
				.gpsLocation(gpsLocation)
				.userId(artistBuildContext.userId())
				.birthdate(request.birthdate())
				.gender(request.gender())
				.avatarUrl(artistBuildContext.avatarUrl())
				.avatarCloudinaryPublicId(artistBuildContext.avatarCloudinaryPublicId())
				.artistName(request.artistName())
				.instagramUrl(artistBuildContext.instagramUrl())
				.facebookUrl(artistBuildContext.facebookUrl())
				.tiktokUrl(artistBuildContext.tiktokUrl())
				.description(artistBuildContext.description())
				.residence(request.residence())
				.otherRoleNames(request.otherRoleNames())
				.otherMusicGenreNames(request.otherMusicGenreNames())
				.build();
	}
	
	public ArtistRegistrationResponse toArtistRegistrationResponse(Artist artist, List<RoleResponse> roles, 
			List<MusicGenreResponse> musicGenres) {
		return new ArtistRegistrationResponse(
				buildArtistResponse(artist, roles, musicGenres)
		);
	}
	
	public ArtistProfileUpdateResponse toArtistProfileUpdateResponse(Artist artist, List<RoleResponse> roles, 
			List<MusicGenreResponse> musicGenres) {
		return new ArtistProfileUpdateResponse(
				buildArtistResponse(artist, roles, musicGenres),
				artist.getAvatarUrl(),
				artist.getInstagramUrl(),
				artist.getFacebookUrl(),
				artist.getTiktokUrl(),
				artist.getDescription()
		);
	}
	
	private ArtistResponse buildArtistResponse(Artist artist, List<RoleResponse> roles, List<MusicGenreResponse> musicGenres) {
		return new ArtistResponse(
				artist.getId(),
				roles,
				musicGenres,
				artist.getBirthdate(),
				artist.isGender(),
				artist.getArtistName(),
				artist.getResidence(),
				artist.getOtherRoleNames(),
				artist.getOtherMusicGenreNames()
			);
	}
}
