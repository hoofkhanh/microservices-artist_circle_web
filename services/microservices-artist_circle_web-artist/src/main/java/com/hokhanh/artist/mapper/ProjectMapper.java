package com.hokhanh.artist.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.model.Artist;
import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.model.Project;
import com.hokhanh.artist.request.project.ProjectRequest;
import com.hokhanh.artist.response.common.MusicGenreResponse;
import com.hokhanh.artist.response.project.common.ArtistSummaryResponse;
import com.hokhanh.artist.response.project.common.ProjectResponse;
import com.hokhanh.artist.response.project.create.ProjectCreationResponse;
import com.hokhanh.artist.response.project.update.ProjectUpdateResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProjectMapper {
	private final MusicGenreMapper musicGenreMapper;
	

	public Project toProject(ProjectRequest request, ProjectBuildContext projectBuildContext, Artist poster,
				List<Artist> collaborators, List<MusicGenre> musicGenres
			) {
		return Project.builder()
			.id(projectBuildContext.id())
			.poster(poster)
			.collaborators(collaborators)
			.musicGenres(musicGenres)
			.customCollaborators(request.customCollaborators())
			.name(request.name())
			.description(request.description())
			.musicUrl(request.musicUrl())
			.musicCloudinaryPublicId(request.musicCloudinaryPublicId())
			.imageUrl(request.imageUrl())
			.imageCloudinaryPublicId(request.imageCloudinaryPublicId())
			.duration(request.duration())
			.otherMusicGenreNames(request.otherMusicGenreNames())
			.build();
	}
	
	public ProjectCreationResponse toProjectCreationResponse(
		Project project, Artist poster,
		List<Artist> collaborators, List<MusicGenre> musicGenres
	) {
		return new ProjectCreationResponse(
			buildProjectResponse(
				project, 
				toArtistSummaryResponse(poster), 
				toArtistSummaryResponseList(collaborators), 
				musicGenreMapper.toMusicGenreResponseList(musicGenres)
			)
		);
	}
	
	public ProjectUpdateResponse toProjectUpdateResponse(
			Project project, Artist poster,
			List<Artist> collaborators, List<MusicGenre> musicGenres
	) {
			return new ProjectUpdateResponse(
				buildProjectResponse(
					project, 
					toArtistSummaryResponse(poster), 
					toArtistSummaryResponseList(collaborators), 
					musicGenreMapper.toMusicGenreResponseList(musicGenres)
				)
			);
	}
	
	private List<ArtistSummaryResponse> toArtistSummaryResponseList(List<Artist> collaborators){
		if(collaborators == null) {
			return null;
		}
		
		return collaborators.stream()
			.map(c -> toArtistSummaryResponse(c)).toList();
	}
	
	private ArtistSummaryResponse toArtistSummaryResponse(Artist collaborator){
		return new ArtistSummaryResponse(
			collaborator.getId(), 
			collaborator.getArtistName(), 
			collaborator.getAvatarUrl()
		);
	}
	
	private ProjectResponse buildProjectResponse(
		Project project, ArtistSummaryResponse poster,
		List<ArtistSummaryResponse> collaborators, List<MusicGenreResponse> musicGenres
	) {
		return new ProjectResponse(
			project.getId(), 
			poster, 
			collaborators, 
			musicGenres, 
			project.getCustomCollaborators(), 
			project.getName(), 
			project.getDescription(), 
			project.getMusicUrl(), 
			project.getMusicCloudinaryPublicId(), 
			project.getImageUrl(), 
			project.getImageCloudinaryPublicId(), 
			project.getDuration(), 
			project.getOtherMusicGenreNames()
		);
	}
	
	
}
