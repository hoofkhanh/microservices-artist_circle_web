package com.hokhanh.artist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.client.UserClient;
import com.hokhanh.artist.mapper.ArtistMapper;
import com.hokhanh.artist.mapper.MusicGenreMapper;
import com.hokhanh.artist.mapper.RoleMapper;
import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.model.Role;
import com.hokhanh.artist.repository.ArtistRepository;
import com.hokhanh.artist.repository.MusicGenreRepository;
import com.hokhanh.artist.repository.RoleRepository;
import com.hokhanh.artist.request.ArtistRequest;
import com.hokhanh.artist.response.artist.ArtistApiResponse;
import com.hokhanh.artist.response.common.StatusType;
import com.hokhanh.common.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService {
	private final ArtistRepository artistRepository;
	private final RoleRepository roleRepository;
	private final MusicGenreRepository musicGenreRepository;
	private final ArtistMapper artistMapper;
	private final RoleMapper roleMapper;
	private final MusicGenreMapper musicGenreMapper;
	private final UserClient userClient;
	
	public ArtistApiResponse register(ArtistRequest request) {
		Long userId = request.userId();
		ArtistApiResponse error = validateUserId(userId);
		if(error != null) {
			return error;
		}
		
		List<ArtistApiResponse> errors = new ArrayList<>();
		
		List<Role> roles = validateAndFetch(
					 request.roleIds(),
					 request.otherRoleNames(),
					 "Missing role",
					 StatusType.NO_ROLE_ASSIGNED,
					 roleRepository::findAllById,
					 errors
				);
		
		List<MusicGenre> musicGenres = validateAndFetch(
				 request.musicGenreIds(),
				 request.otherMusicGenreNames(),
				 "Missing music genre",
				 StatusType.NO_MUSIC_GENRE_ASSIGNED,
				 musicGenreRepository::findAllById,
				 errors
			);
		
		if(errors.size() > 0) {
			return errors.get(0);
		}
		
		return saveAndReturnArtistApiResponse(request, userId, roles, musicGenres);
	}
	
//	if update request.userId() == null because userId get from cookie, positve doesn't check >0 when it null
	public ArtistApiResponse updateProfile(ArtistRequest request, Long userId) {
		Artist artist = artistRepository.findById(request.id()).orElse(null);
		if(artist == null) {
			return new ArtistApiResponse(false, "Artist is not found", StatusType.ARTIST_NOT_FOUND, null);
		}
		return null;
	}
	
	private ArtistApiResponse validateUserId(Long userId) {
		if(userId == null || !userClient.checkUserExistsInternal(userId)){
			return new ArtistApiResponse(false, "User is not found (userId)", StatusType.USER_NOT_FOUND, null);
		}
		
		if(artistRepository.existsByUserId(userId)) {
			return new ArtistApiResponse(false, "User was registered (userId)", StatusType.USER_REGISTERED, null);
		}
		
		return null;
	}
	
	private <T> List<T> validateAndFetch(
		    List<Long> ids,
		    String otherNames,
		    String message,
		    StatusType status,
		    Function<List<Long>, List<T>> repositoryFetcher,
		    List<ArtistApiResponse> errorHolder
		) {
		    boolean noOtherNames = otherNames == null || otherNames.isBlank();
		    boolean noIds = ids == null || ids.isEmpty();

		    if (noOtherNames && noIds) {
		        errorHolder.add(new ArtistApiResponse(false, message, status, null));
		        return null;
		    }

		    return !noIds ? repositoryFetcher.apply(ids) : null;
	}

	private ArtistApiResponse saveAndReturnArtistApiResponse(
			ArtistRequest request, Long userId, List<Role> roles, List<MusicGenre> musicGenres) {
		Artist artist = artistMapper.toArtist(request, userId, roles, musicGenres, null, null);
		
		artist.setAvatarUrl(StringUtils.cleanBlank(artist.getAvatarUrl()));
		artist.setArtistName(StringUtils.cleanBlank(artist.getArtistName()));
		artist.setInstagramUrl(StringUtils.cleanBlank(artist.getInstagramUrl()));
		artist.setFacebookUrl(StringUtils.cleanBlank(artist.getFacebookUrl()));
		artist.setTiktokUrl(StringUtils.cleanBlank(artist.getTiktokUrl()));
		artist.setDescription(StringUtils.cleanBlank(artist.getDescription()));
		artist.setResidence(StringUtils.cleanBlank(artist.getResidence()));
		artist.setOtherRoleNames(StringUtils.cleanBlank(artist.getOtherRoleNames()));
		artist.setOtherMusicGenreNames(StringUtils.cleanBlank(artist.getOtherMusicGenreNames()));
		
		artist = artistRepository.save(artist);
		
		return new ArtistApiResponse(true, "Register successfully", null,
					artistMapper.toArtistResponse(
							artist, 
							roleMapper.toRoleResponseList(artist.getRoles()),
							musicGenreMapper.toMusicGenreResponseList(artist.getMusicGenres()),
							null,
							null
					)
				);
	}
	
	

}
