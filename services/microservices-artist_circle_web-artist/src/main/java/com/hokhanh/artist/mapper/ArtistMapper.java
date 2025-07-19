package com.hokhanh.artist.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.GpsLocation;
import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.model.Project;
import com.hokhanh.artist.model.Role;
import com.hokhanh.artist.request.artist.ArtistRequest;
import com.hokhanh.artist.response.artist.common.ArtistResponse;
import com.hokhanh.artist.response.artist.common.RoleResponse;
import com.hokhanh.artist.response.artist.create.ArtistRegistrationResponse;
import com.hokhanh.artist.response.artist.search.ArtistSearchResponse;
import com.hokhanh.artist.response.artist.update.ArtistProfileUpdateResponse;
import com.hokhanh.artist.response.common.MusicGenreResponse;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ArtistMapper {
	private final MusicGenreMapper musicGenreMapper;
	private final ProjectMapper projectMapper;
	private final GpsLocationMapper gpsLocationMapper;
	
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
	
	public ArtistRegistrationResponse toArtistRegistrationResponse(Artist artist, List<Role> roles, 
			List<MusicGenre> musicGenres) {
		return new ArtistRegistrationResponse(
				buildArtistResponse(
					artist, 
					toRoleResponseList(roles),
					musicGenreMapper.toMusicGenreResponseList(musicGenres)
				)
		);
	}
	
	public ArtistProfileUpdateResponse toArtistProfileUpdateResponse(Artist artist, List<Role> roles, 
			List<MusicGenre> musicGenres) {
		return new ArtistProfileUpdateResponse(
				buildArtistResponse(
					artist, 
					toRoleResponseList(roles),
					musicGenreMapper.toMusicGenreResponseList(musicGenres)
				),
				artist.getAvatarUrl(),
				artist.getInstagramUrl(),
				artist.getFacebookUrl(),
				artist.getTiktokUrl(),
				artist.getDescription()
		);
	}
	
	public ArtistSearchResponse toArtistSearchResponse(Artist artist) {
		return new ArtistSearchResponse(
				buildArtistResponse(
					artist, 
					toRoleResponseList(artist.getRoles()),
					musicGenreMapper.toMusicGenreResponseList(artist.getMusicGenres())
				),
				artist.getAvatarUrl(),
				artist.getAvatarCloudinaryPublicId(),
				artist.getInstagramUrl(),
				artist.getFacebookUrl(),
				artist.getTiktokUrl(),
				artist.getDescription(),
				projectMapper.buildProjectResponseList(artist.getProjects()),
				gpsLocationMapper.buidlGpsLocationResponse(artist.getGpsLocation(), artist.getId())
		);
	}
	
	private List<RoleResponse> toRoleResponseList(List<Role> roles) {
		if(roles == null) {
			return null;
		}
		
		return roles
				.stream()
				.map(role -> new RoleResponse(role.getId(), role.getName()))
				.collect(Collectors.toList());
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
