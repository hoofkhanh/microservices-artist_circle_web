package com.hokhanh.artist.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hokhanh.artist.model.MusicGenre;
import com.hokhanh.artist.response.common.MusicGenreResponse;

@Service
public class MusicGenreMapper {
	public List<MusicGenreResponse> toMusicGenreResponseList(List<MusicGenre> musicGenres) {
		return musicGenres
				.stream()
				.map(musicGenre -> new MusicGenreResponse(musicGenre.getId(), musicGenre.getName()))
				.collect(Collectors.toList());
	}
}
