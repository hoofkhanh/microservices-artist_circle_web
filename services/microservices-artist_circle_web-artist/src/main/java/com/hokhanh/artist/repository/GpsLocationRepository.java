package com.hokhanh.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hokhanh.artist.model.GpsLocation;

public interface GpsLocationRepository extends JpaRepository<GpsLocation, Long> {

}
