package com.hokhanh.artist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.client.UserClient;
import com.hokhanh.artist.mapper.ArtistBuildContext;
import com.hokhanh.artist.mapper.ArtistMapper;
import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.GpsLocation;
import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.model.Project;
import com.hokhanh.artist.model.Role;
import com.hokhanh.artist.rabbitMq.ArtistRabbitMqProducer;
import com.hokhanh.artist.repository.ArtistRepository;
import com.hokhanh.artist.repository.MusicGenreRepository;
import com.hokhanh.artist.repository.RoleRepository;
import com.hokhanh.artist.request.artist.ArtistProfileUpdateRequest;
import com.hokhanh.artist.request.artist.ArtistRegistrationRequest;
import com.hokhanh.artist.response.artist.create.ArtistRegistrationApiResponse;
import com.hokhanh.artist.response.artist.search.ArtistSearchDetailApiResponse;
import com.hokhanh.artist.response.artist.update.ArtistProfileUpdateApiResponse;
import com.hokhanh.artist.response.common.ApiResponse;
import com.hokhanh.artist.response.common.StatusType;
import com.hokhanh.artist.util.RepositoryUtils;
import com.hokhanh.common.artist.response.ArtistSearchResponse;
import com.hokhanh.common.gpsLocation.response.ArtistGpsLocationResponse;
import com.hokhanh.common.rabbitMq.dto.ArtistMessage;
import com.hokhanh.common.rabbitMq.dto.ArtistSearchHistoryMessage;
import com.hokhanh.common.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService {
	private final ArtistRepository artistRepository;
	private final RoleRepository roleRepository;
	private final MusicGenreRepository musicGenreRepository;
	private final ArtistMapper artistMapper;
	private final UserClient userClient;
	private final ArtistRabbitMqProducer artistRabbitMqProducer;
	
	public ArtistRegistrationApiResponse register(ArtistRegistrationRequest request) {
		Long userId = request.userId();
		ArtistRegistrationApiResponse error = validateUserId(userId);
		if(error != null) {
			return error;
		}
		
		List<ArtistRegistrationApiResponse> errors = new ArrayList<>();
		
		List<Role> roles = RepositoryUtils.validateAndFetch(
					 request.artist().roleIds(),
					 request.artist().otherRoleNames(),
					 roleRepository::findAllById,
					 errors,
					 new ArtistRegistrationApiResponse(
	    				new ApiResponse(false, "Missing role", StatusType.NO_ROLE_ASSIGNED),
	    				null
					 )
				);
		
		List<MusicGenre> musicGenres = RepositoryUtils.validateAndFetch(
				 request.artist().musicGenreIds(),
				 request.artist().otherMusicGenreNames(),
				 musicGenreRepository::findAllById,
				 errors,
				 new ArtistRegistrationApiResponse(
					new ApiResponse(false, "Missing music genre", StatusType.NO_MUSIC_GENRE_ASSIGNED),
					null
				 )
			);
		
		if(errors.size() > 0) {
			return errors.get(0);
		}
		
		return saveAndReturnArtistRegistrationApiResponse(request, userId, roles, musicGenres);
	}
	
	public ArtistProfileUpdateApiResponse update(ArtistProfileUpdateRequest request, String userId) {
		Long userIdLong = Long.parseLong(userId);
		Artist artist = artistRepository.findByUserId(userIdLong);
		if(artist == null) {
			return new ArtistProfileUpdateApiResponse(new ApiResponse(false, "Not found artist", StatusType.ARTIST_NOT_FOUND), null);
		}
		
		List<ArtistProfileUpdateApiResponse> errors = new ArrayList<>();
		
		List<Role> roles = RepositoryUtils.validateAndFetch(
				 request.artist().roleIds(),
				 request.artist().otherRoleNames(),
				 roleRepository::findAllById,
				 errors,
				 new ArtistProfileUpdateApiResponse(
	   				new ApiResponse(false, "Missing role", StatusType.NO_ROLE_ASSIGNED),
	   				null
				 )
		);

	List<MusicGenre> musicGenres = RepositoryUtils.validateAndFetch(
			 request.artist().musicGenreIds(),
			 request.artist().otherMusicGenreNames(),
			 musicGenreRepository::findAllById,
			 errors,
			 new ArtistProfileUpdateApiResponse(
				new ApiResponse(false, "Missing music genre", StatusType.NO_MUSIC_GENRE_ASSIGNED),
				null
			 )
		);
		
		if(errors.size() > 0) {
			return errors.get(0);
		}
		
		GpsLocation gpsLocationClone = artist.getGpsLocation()!= null? new GpsLocation(artist.getGpsLocation()):null;
		List<Project> projectClones = artist.getProjects() != null && !artist.getProjects().isEmpty() 
				? cloneProjects(artist.getProjects()):null;
		
		return saveAndReturnArtistProfileUpdateApiResponse(
			request, artist.getId(), artist.getUserId(), roles, musicGenres, 
			projectClones, gpsLocationClone
		);
	}
	
	public ArtistSearchDetailApiResponse findByUserId(String userId) {
		Artist artist = artistRepository.findByUserId(Long.parseLong(userId));
		if(artist == null) {
			return new ArtistSearchDetailApiResponse(new ApiResponse(false, "Artist was not found", StatusType.ARTIST_NOT_FOUND), null);
		}
		
		return new ArtistSearchDetailApiResponse(
				new ApiResponse(true, "Search yourself successfully", null),
				artistMapper.toArtistSearchDetailResponse(artist, true)
			);
	}
	
	public ArtistSearchDetailApiResponse findById(Long artistId, String userId) {
		Artist targetArtist = artistRepository.findById(artistId).orElse(null);
		if(targetArtist == null) {
			return new ArtistSearchDetailApiResponse(
					new ApiResponse(false, "Artist was not found", StatusType.ARTIST_NOT_FOUND),
					null
				);
		}
		
		Artist searcherArtist = artistRepository.findByUserId(Long.parseLong(userId));
		if(searcherArtist == null) {
			return new ArtistSearchDetailApiResponse(
					new ApiResponse(false, "Artist was not found (myselft)", StatusType.ARTIST_NOT_FOUND),
					null
				);
		}
		
		if(targetArtist.getId().equals(searcherArtist.getId())) {
			return new ArtistSearchDetailApiResponse(
					new ApiResponse(false, "Can't search yourself", StatusType.SEARCH_YOUR_SELF),
					null
				);
		}
		
		Long searcherArtistId = searcherArtist.getId();
		Long targetArtistId = targetArtist.getId();
		artistRabbitMqProducer.sendArtistSearchHistoryMessage(
					new ArtistSearchHistoryMessage(searcherArtistId, targetArtistId)
				);
		
		
		return new ArtistSearchDetailApiResponse(
				new ApiResponse(true, "Search artist successfully", null),
				artistMapper.toArtistSearchDetailResponse(targetArtist, false)
			);
	}
	
	// definitely order remains 
	public List<ArtistSearchResponse> searchArtists(List<Long> ids) {
		List<Artist> artists = artistRepository.findAllById(ids);

		Map<Long, Artist> artistMap = artists.stream()
			    .collect(Collectors.toMap(
			        a -> a.getId(),
			        a -> a
			    ));

	    return ids.stream()
	    	    .map(id -> artistMap.get(id))
	    	    .filter(a -> a != null)
	    	    .map(a -> new ArtistSearchResponse(
	    	        a.getId(),
	    	        a.getArtistName(),
	    	        a.getAvatarUrl(),
	    	        a.getResidence(),
	    	        null
	    	    ))
	    	    .toList();
	}
	
	public ArtistGpsLocationResponse getMyGpsLocationAndArtistId(Long userId) {
		Artist artist = artistRepository.findByUserId(userId);
		if(artist == null) {
			return null;
		}
		
		return new ArtistGpsLocationResponse(artist.getGpsLocation().getLongitude(), 
				artist.getGpsLocation().getLatitude(), artist.getId());
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
	
	

	private ArtistRegistrationApiResponse saveAndReturnArtistRegistrationApiResponse(
			ArtistRegistrationRequest request, Long userId, List<Role> roles, List<MusicGenre> musicGenres) {
		Artist artist = artistMapper.toArtist(
			request.artist(),
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
		
		cleanArtistStringProps(artist);
		
		artist = artistRepository.save(artist);
		
		artistRabbitMqProducer.sendArtistMessage(
			new ArtistMessage(artist.getId(), artist.getArtistName(), null, null)
		);
		
		return new ArtistRegistrationApiResponse(
			new ApiResponse(true, "Register successfully", null),
			artistMapper.toArtistRegistrationResponse(
				artist, 
				roles,
				musicGenres
			)
		);
	}
	
	private ArtistProfileUpdateApiResponse saveAndReturnArtistProfileUpdateApiResponse(
			ArtistProfileUpdateRequest request, Long artistId, Long userId, List<Role> roles, List<MusicGenre> musicGenres,
			List<Project> projects, GpsLocation gpsLocation) {
		Artist artist = artistMapper.toArtist(
			request.artist(),
			new ArtistBuildContext(
				userId,
				artistId,
				request.avatarUpload().secureUrl(),
				request.avatarUpload().publicId(),
				request.instagramUrl(),
				request.facebookUrl(),
				request.tiktokUrl(),
				request.description()
			), 
			roles, 
			musicGenres, 
			projects, 
			gpsLocation
		);
		
		cleanArtistStringProps(artist);
		
		artist = artistRepository.save(artist);
		
		artistRabbitMqProducer.sendArtistMessage(
			new ArtistMessage(
				artist.getId(), artist.getArtistName(), 
				artist.getGpsLocation().getLongitude(), artist.getGpsLocation().getLatitude()
			)
		);
		
		return new ArtistProfileUpdateApiResponse(
			new ApiResponse(true, "Update successfully", null),
			artistMapper.toArtistProfileUpdateResponse(
				artist, 
				roles,
				musicGenres
			)
		);
	}

	
	private void cleanArtistStringProps(Artist artist) {
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
	}
	
	private List<Project> cloneProjects(List<Project> originalProjects) {
	    return originalProjects.stream()
	            .map(p -> new Project(p))
	            .collect(Collectors.toList());
	}

	
	
	

	
}
