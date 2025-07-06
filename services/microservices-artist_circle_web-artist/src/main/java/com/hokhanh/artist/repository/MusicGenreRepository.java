package com.hokhanh.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hokhanh.artist.model.MusicGenre;

public interface MusicGenreRepository extends JpaRepository<MusicGenre, Long> {

}
