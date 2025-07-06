package com.hokhanh.artist.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;


public record ArtistRequest(
	@Positive(message = "roleId must be a positive number")
	Long id,
		
	// k có @Empty vì roleIds đc null
	@Valid
	List<
		@NotNull(message = "Each roleId must not be null")
		@Positive(message = "Each roleId must be a positive number")
	Long> roleIds,
	
	// k có @Empty vì musicGenreIds đc null
	@Valid
	List<
		@NotNull(message = "Each musicGenreId must not be null")
		@Positive(message = "Each musicGenreId must be a positive number")
	Long> musicGenreIds,
	
//	if update request.userId() == null because userId get from cookie, positve doesn't check >0 when it null
	@Positive(message = "userId must be a positive number")
	Long userId,
	
	@NotNull(message = "birthdate is mandatory")
	@Past(message = "birthdate must be in the past")
	LocalDate birthdate,
	
	@NotNull(message = "gender is mandatory")
	Boolean gender,
	
	@NotBlank(message = "avatarUrl is mandatory")
	String avatarUrl,
	
	@NotBlank(message = "artistName is mandatory")
	String artistName,
	
	String instagramUrl,
	String facebookUrl,
	String tiktokUrl,
	String description,
	
	@NotBlank(message = "residence is mandatory")
	String residence,
	String otherRoleNames,
	String otherMusicGenreNames
) {

}
