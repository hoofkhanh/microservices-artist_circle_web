package com.hokhanh.artist.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.hokhanh.artist.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
	boolean existsByUserId(Long userId);
	Artist findByUserId(Long userId);
}
