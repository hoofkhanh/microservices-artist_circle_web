package com.hokhanh.artist.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(
			name = "artist_role",
			joinColumns = @JoinColumn(name = "artist_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;
	
	@ManyToMany
	@JoinTable(
			name = "artist_musicGenre",
			joinColumns = @JoinColumn(name = "artist_id"),
			inverseJoinColumns = @JoinColumn(name = "music_genre_id")
	)
	private List<MusicGenre> musicGenres;
	
	@OneToMany(mappedBy = "poster", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Project> projects;
	
	@OneToOne
	private GpsLocation gpsLocation;
	
	private Long userId;
	private LocalDate birthdate;
	private boolean gender;
	
	@Column(columnDefinition = "TEXT")
	private String avatarUrl;
	@Column(columnDefinition = "TEXT")
	private String avatarCloudinaryPublicId;
	
	private String artistName;
	
	@Column(columnDefinition = "TEXT")
	private String instagramUrl;
	
	@Column(columnDefinition = "TEXT")
	private String facebookUrl;
	
	@Column(columnDefinition = "TEXT")
	private String tiktokUrl;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private String residence;
	
	@Column(columnDefinition = "TEXT")
	private String otherRoleNames;
	
	@Column(columnDefinition = "TEXT")
	private String otherMusicGenreNames;
}
