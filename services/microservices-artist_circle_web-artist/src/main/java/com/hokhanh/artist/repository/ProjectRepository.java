package com.hokhanh.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hokhanh.artist.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
