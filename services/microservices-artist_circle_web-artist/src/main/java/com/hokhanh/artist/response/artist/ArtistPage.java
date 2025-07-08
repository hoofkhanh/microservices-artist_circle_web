package com.hokhanh.artist.response.artist;

import java.util.List;

import com.hokhanh.artist.response.artist.common.ArtistResponse;
import com.hokhanh.artist.response.common.PageInfo;

public record ArtistPage(
	List<ArtistResponse> content,
	PageInfo page
) {

}
