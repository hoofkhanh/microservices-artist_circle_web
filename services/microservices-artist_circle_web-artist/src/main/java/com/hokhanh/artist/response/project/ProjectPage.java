package com.hokhanh.artist.response.project;

import java.util.List;

import com.hokhanh.artist.response.common.PageInfo;
import com.hokhanh.artist.response.project.common.ProjectResponse;

public record ProjectPage(
	List<ProjectResponse> content,
	PageInfo page
) {

}
