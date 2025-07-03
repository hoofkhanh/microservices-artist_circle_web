package com.hokhanh.artist.response.project;

import java.util.List;

import com.hokhanh.artist.response.common.PageInfo;

public record ProjectPage(
	List<ProjectResponse> content,
	PageInfo page
) {

}
