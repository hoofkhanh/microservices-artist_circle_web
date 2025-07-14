package com.hokhanh.artist.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Artist poster;

	@ManyToMany
	@JoinTable(name = "project_artist", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
	private List<Artist> collaborators;

	@ManyToMany
	@JoinTable(name = "project_musicGenre", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "music_genre_id"))
	private List<MusicGenre> musicGenres;

	@Column(columnDefinition = "TEXT")
	private String customCollaborators;

	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "TEXT")
	private String musicUrl;
	@Column(columnDefinition = "TEXT")
	private String musicCloudinaryPublicId;

	@Column(columnDefinition = "TEXT")
	private String imageUrl;
	@Column(columnDefinition = "TEXT")
	private String imageCloudinaryPublicId;

	private float duration;

	@Column(columnDefinition = "TEXT")
	private String otherMusicGenreNames;
	
	public Project(Project other) {
        this.id = other.id;
        this.poster = other.poster;
        this.collaborators = other.collaborators;
        this.musicGenres = other.musicGenres;
        this.customCollaborators = other.customCollaborators;
        this.name = other.name;
        this.description = other.description;
        this.musicUrl = other.musicUrl;
        this.musicCloudinaryPublicId = other.musicCloudinaryPublicId;
        this.imageUrl = other.imageUrl;
        this.imageCloudinaryPublicId = other.imageCloudinaryPublicId;
        this.duration = other.duration;
        this.otherMusicGenreNames = other.otherMusicGenreNames;
    }


}
