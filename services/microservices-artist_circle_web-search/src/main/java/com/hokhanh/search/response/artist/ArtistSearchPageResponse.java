package com.hokhanh.search.response.artist;

import java.util.List;

import com.hokhanh.common.artist.response.ArtistSearchResponse;
import com.hokhanh.search.response.page.PageInfoResponse;


public record ArtistSearchPageResponse(
	List<ArtistSearchResponse> artists,
	PageInfoResponse pageInfo
) {

}
