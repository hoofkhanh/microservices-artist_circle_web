package com.hokhanh.artist.request.artist;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;


public record ArtistRequest(
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
	
	@NotNull(message = "birthdate is mandatory")
	@Past(message = "birthdate must be in the past")
	LocalDate birthdate,
	
	@NotNull(message = "gender is mandatory")
	Boolean gender,
	
	@NotBlank(message = "artistName is mandatory")
	String artistName,
	
	@NotBlank(message = "residence is mandatory")
	String residence,
	
	String otherRoleNames,
	String otherMusicGenreNames
) {

}
