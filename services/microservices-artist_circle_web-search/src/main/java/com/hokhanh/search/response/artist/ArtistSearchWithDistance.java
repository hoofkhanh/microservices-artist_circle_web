package com.hokhanh.search.response.artist;

import com.hokhanh.search.model.ArtistSearch;

public record ArtistSearchWithDistance(
	ArtistSearch artist,
    double distanceInMeters
) {

}
