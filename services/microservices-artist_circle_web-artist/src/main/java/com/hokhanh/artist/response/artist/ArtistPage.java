package com.hokhanh.artist.response.artist;

import java.util.List;

import com.hokhanh.artist.response.artist.common.ArtistResponse;
import com.hokhanh.artist.response.common.PageInfo;

public record ArtistPage(
	// chỉnh thành 1 list ArtistFindResponse chứa các cột artistResponse, projectResponse ,...
	List<ArtistResponse> content,
	PageInfo page
) {

}
