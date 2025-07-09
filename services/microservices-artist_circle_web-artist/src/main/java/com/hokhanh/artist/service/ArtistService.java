package com.hokhanh.artist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.client.UserClient;
import com.hokhanh.artist.mapper.ArtistBuildContext;
import com.hokhanh.artist.mapper.ArtistMapper;
import com.hokhanh.artist.mapper.MusicGenreMapper;
import com.hokhanh.artist.mapper.RoleMapper;
import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.model.Role;
import com.hokhanh.artist.repository.ArtistRepository;
import com.hokhanh.artist.repository.MusicGenreRepository;
import com.hokhanh.artist.repository.RoleRepository;
import com.hokhanh.artist.request.artist.ArtistProfileUpdateRequest;
import com.hokhanh.artist.request.artist.ArtistRegistrationRequest;
import com.hokhanh.artist.request.artist.ArtistRequest;
import com.hokhanh.artist.response.artist.profileUpdate.ArtistProfileUpdateApiResponse;
import com.hokhanh.artist.response.artist.registration.ArtistRegistrationApiResponse;
import com.hokhanh.artist.response.common.ApiResponse;
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
	
	public ArtistRegistrationApiResponse register(ArtistRegistrationRequest request) {
		Long userId = request.userId();
		ArtistRegistrationApiResponse error = validateUserId(userId);
		if(error != null) {
			return error;
		}
		
		List<ArtistRegistrationApiResponse> errors = new ArrayList<>();
		
		List<Role> roles = validateAndFetch(
					 request.artist().roleIds(),
					 request.artist().otherRoleNames(),
					 "Missing role",
					 StatusType.NO_ROLE_ASSIGNED,
					 roleRepository::findAllById,
					 errors
				);
		
		List<MusicGenre> musicGenres = validateAndFetch(
				 request.artist().musicGenreIds(),
				 request.artist().otherMusicGenreNames(),
				 "Missing music genre",
				 StatusType.NO_MUSIC_GENRE_ASSIGNED,
				 musicGenreRepository::findAllById,
				 errors
			);
		
		if(errors.size() > 0) {
			return errors.get(0);
		}
		
		return saveAndReturnArtistRegistrationApiResponse(request.artist(), userId, roles, musicGenres);
	}
	
	public ArtistProfileUpdateApiResponse update(ArtistProfileUpdateRequest request) {
		// ktra artist
		Artist artist = artistRepository.findById(request.id()).orElse(null);
		
		// check xem ảnh có ở clodinary k
		
		return null;
	}
	
	private ArtistRegistrationApiResponse validateUserId(Long userId) {
		if(userId == null || !userClient.checkUserExistsInternal(userId)){
			return new ArtistRegistrationApiResponse(
					new ApiResponse(false, "User is not found (userId)", StatusType.USER_NOT_FOUND)
					, null
			);
		}
		
		if(artistRepository.existsByUserId(userId)) {
			return new ArtistRegistrationApiResponse(
					new ApiResponse(false, "User was registered (userId)", StatusType.USER_REGISTERED)
					, null);
		}
		
		return null;
	}
	
	private <T> List<T> validateAndFetch(
		    List<Long> ids,
		    String otherNames,
		    String message,
		    StatusType status,
		    Function<List<Long>, List<T>> repositoryFetcher,
		    List<ArtistRegistrationApiResponse> errorHolder
		) {
		    boolean noOtherNames = otherNames == null || otherNames.isBlank() || StringUtils.cleanListString(otherNames) == null;
		    boolean noIds = ids == null || ids.isEmpty();

		    if (noOtherNames && noIds) {
		        errorHolder.add(
	        		new ArtistRegistrationApiResponse(
	        				new ApiResponse(false, message, status),
	        				null
    				)
        		);
		        return null;
		    }
		    
		    if(!noIds) {
		    	List<T> results = repositoryFetcher.apply(ids);
		    	if(results.isEmpty() && noOtherNames) {
		    		errorHolder.add(
			        		new ArtistRegistrationApiResponse(
			        				new ApiResponse(false, message, status),
			        				null
		    				)
		        		);
			        return null;
		    	}
		    	
		    	return results;
		    }
		    
		    return null;
	}

	private ArtistRegistrationApiResponse saveAndReturnArtistRegistrationApiResponse(
			ArtistRequest request, Long userId, List<Role> roles, List<MusicGenre> musicGenres) {
		roles = roles != null && !roles.isEmpty() ? roles : null;
		musicGenres = musicGenres != null && !musicGenres.isEmpty() ? musicGenres : null;
		
		Artist artist = artistMapper.toArtist(
			request,
			new ArtistBuildContext(
				userId,
				null,
				null,
				null,
				null,
				null,
				null,
				null
			), 
			roles, 
			musicGenres, 
			null, 
			null
		);
		
		artist.setAvatarUrl(StringUtils.cleanBlank(artist.getAvatarUrl()));
		artist.setAvatarCloudinaryPublicId(StringUtils.cleanBlank(artist.getAvatarCloudinaryPublicId()));
		artist.setArtistName(StringUtils.cleanBlank(artist.getArtistName()));
		artist.setInstagramUrl(StringUtils.cleanBlank(artist.getInstagramUrl()));
		artist.setFacebookUrl(StringUtils.cleanBlank(artist.getFacebookUrl()));
		artist.setTiktokUrl(StringUtils.cleanBlank(artist.getTiktokUrl()));
		artist.setDescription(StringUtils.cleanBlank(artist.getDescription()));
		artist.setResidence(StringUtils.cleanBlank(artist.getResidence()));
		artist.setOtherRoleNames(StringUtils.cleanListString(artist.getOtherRoleNames()));
		artist.setOtherMusicGenreNames(StringUtils.cleanListString(artist.getOtherMusicGenreNames()));
		
		artist = artistRepository.save(artist);
		
		return new ArtistRegistrationApiResponse(
			new ApiResponse(true, "Register successfully", null),
			artistMapper.toArtistRegistrationResponse(
				artist, 
				roles != null ? roleMapper.toRoleResponseList(roles) : null,
				musicGenres != null ?  musicGenreMapper.toMusicGenreResponseList(musicGenres): null
			)
		);
	}

	
	
	

}
