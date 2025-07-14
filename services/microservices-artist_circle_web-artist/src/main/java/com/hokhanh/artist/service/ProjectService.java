package com.hokhanh.artist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.mapper.ProjectBuildContext;
import com.hokhanh.artist.mapper.ProjectMapper;
import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.model.Project;
import com.hokhanh.artist.repository.ArtistRepository;
import com.hokhanh.artist.repository.MusicGenreRepository;
import com.hokhanh.artist.repository.ProjectRepository;
import com.hokhanh.artist.request.project.ProjectCreationRequest;
import com.hokhanh.artist.request.project.ProjectUpdateRequest;
import com.hokhanh.artist.response.common.ApiResponse;
import com.hokhanh.artist.response.common.StatusType;
import com.hokhanh.artist.response.project.create.ProjectCreationApiResponse;
import com.hokhanh.artist.response.project.update.ProjectUpdateApiResponse;
import com.hokhanh.artist.util.RepositoryUtils;
import com.hokhanh.common.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ArtistRepository artistRepository;
	private final MusicGenreRepository musicGenreRepository;
	private final ProjectRepository projectRepository;
	private final ProjectMapper projectMapper;

	public ProjectCreationApiResponse createProject(ProjectCreationRequest request, String userId) {
		Long userIdLong = Long.parseLong(userId);
		Artist poster = artistRepository.findByUserId(userIdLong);
		if(poster == null) {
			return new ProjectCreationApiResponse(new ApiResponse(false, "Poster was not found", StatusType.ARTIST_NOT_FOUND), null);
		}
		
		List<ProjectCreationApiResponse> errors = new ArrayList<>();
		
		List<Artist> collaborators = RepositoryUtils.validateAndFetch(
				 request.project().collaboratorIds() ,
				 request.project().customCollaborators(),
				 artistRepository::findAllById,
				 errors,
				 new ProjectCreationApiResponse(
	   				new ApiResponse(false, "Missing collaborator", StatusType.NO_COLLABORATOR_ASSIGNED),
	   				null
				 )
			);
		
		List<MusicGenre> musicGenres = RepositoryUtils.validateAndFetch(
				 request.project().musicGenreIds() ,
				 request.project().otherMusicGenreNames(),
				 musicGenreRepository::findAllById,
				 errors,
				 new ProjectCreationApiResponse(
	   				new ApiResponse(false, "Missing musicGenre", StatusType.NO_MUSIC_GENRE_ASSIGNED),
	   				null
				 )
			);
		
		if(errors.size() > 0) {
			return errors.get(0);
		}
		
		return saveAndReturnProjectCreationApiResponse(request, poster, collaborators, musicGenres);
	}
	
	public ProjectUpdateApiResponse updateProject(ProjectUpdateRequest request, String userId) {
		Long userIdLongFromToken = Long.parseLong(userId);
		Artist posterFromToken = artistRepository.findByUserId(userIdLongFromToken);
		if(posterFromToken == null) {
			return new ProjectUpdateApiResponse(new ApiResponse(false, "Poster was not found", StatusType.ARTIST_NOT_FOUND), null);
		}
		
		Project project = projectRepository.findById(request.id()).orElse(null);
		if(project == null) {
			return new ProjectUpdateApiResponse(new ApiResponse(false, "Project was not found", StatusType.PROJECT_NOT_FOUND), null);
		}
		
		if(!project.getPoster().getId().equals(posterFromToken.getId())) {
			return new ProjectUpdateApiResponse(new ApiResponse(false, "You aren't this project's poster", StatusType.YOU_NOT_POSTER), null);
		}
		
		List<ProjectUpdateApiResponse> errors = new ArrayList<>();
		
		List<Artist> collaborators = RepositoryUtils.validateAndFetch(
				 request.project().collaboratorIds() ,
				 request.project().customCollaborators(),
				 artistRepository::findAllById,
				 errors,
				 new ProjectUpdateApiResponse(
	   				new ApiResponse(false, "Missing collaborator", StatusType.NO_COLLABORATOR_ASSIGNED),
	   				null
				 )
			);
		
		List<MusicGenre> musicGenres = RepositoryUtils.validateAndFetch(
				 request.project().musicGenreIds() ,
				 request.project().otherMusicGenreNames(),
				 musicGenreRepository::findAllById,
				 errors,
				 new ProjectUpdateApiResponse(
	   				new ApiResponse(false, "Missing musicGenre", StatusType.NO_MUSIC_GENRE_ASSIGNED),
	   				null
				 )
			);
		
		if(errors.size() > 0) {
			return errors.get(0);
		}
		
		return saveAndReturnProjectUpdateApiResponse(request, posterFromToken, collaborators, musicGenres);
	}
	
	private ProjectCreationApiResponse saveAndReturnProjectCreationApiResponse(
		ProjectCreationRequest request, Artist poster, List<Artist> collaborators,
		List<MusicGenre> musicGenres
	) {
		Project project = projectMapper.toProject(
			request.project(), new ProjectBuildContext(null),
			poster, collaborators, musicGenres);
		
		cleanProjectStringProps(project);
		
		project = projectRepository.save(project);
		
		return new ProjectCreationApiResponse(
			new ApiResponse(true, "Project created successfully.", null), 
			projectMapper.toProjectCreationResponse(
				project, 
				poster, 
				project.getCollaborators(), 
				project.getMusicGenres()
			)
		);
	}
	
	private ProjectUpdateApiResponse saveAndReturnProjectUpdateApiResponse(
			ProjectUpdateRequest request, Artist poster, List<Artist> collaborators,
			List<MusicGenre> musicGenres
	) {
		Project project = projectMapper.toProject(
			request.project(), new ProjectBuildContext(request.id()),
			poster, collaborators, musicGenres);
		
		cleanProjectStringProps(project);
		
		project = projectRepository.save(project);
		
		return new ProjectUpdateApiResponse(
			new ApiResponse(true, "Project created successfully.", null), 
			projectMapper.toProjectUpdateResponse(
				project, 
				poster, 
				project.getCollaborators(), 
				project.getMusicGenres()
			)
		);
	}
	
	private void cleanProjectStringProps(Project project) {
		project.setCustomCollaborators(StringUtils.cleanListString(project.getCustomCollaborators()));
		project.setName(StringUtils.cleanBlank(project.getName()));
		project.setDescription(StringUtils.cleanBlank(project.getDescription()));
		project.setMusicUrl(StringUtils.cleanBlank(project.getMusicUrl()));
		project.setMusicCloudinaryPublicId(StringUtils.cleanBlank(project.getMusicCloudinaryPublicId()));
		project.setImageUrl(StringUtils.cleanBlank(project.getImageUrl()));
		project.setImageCloudinaryPublicId(StringUtils.cleanBlank(project.getImageCloudinaryPublicId()));
		project.setOtherMusicGenreNames(StringUtils.cleanListString(project.getOtherMusicGenreNames()));
	}

	
}
