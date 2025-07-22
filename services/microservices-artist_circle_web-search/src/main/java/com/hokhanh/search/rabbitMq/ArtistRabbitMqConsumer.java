package com.hokhanh.search.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hokhanh.common.rabbitMq.ArtistRabbitMqConstants;
import com.hokhanh.common.rabbitMq.dto.ArtistMessage;
import com.hokhanh.common.rabbitMq.dto.ArtistSearchHistoryMessage;
import com.hokhanh.search.mapper.ArtistSearchHistoryMapper;
import com.hokhanh.search.mapper.ArtistSearchMapper;
import com.hokhanh.search.model.ArtistSearch;
import com.hokhanh.search.model.ArtistSearchHistory;
import com.hokhanh.search.repository.ArtistSearchHistoryRepository;
import com.hokhanh.search.repository.ArtistSearchRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArtistRabbitMqConsumer {
	private final ArtistSearchMapper artistSearchMapper;
	private final ArtistSearchHistoryMapper artistSearchHistoryMapper;
	private final ArtistSearchRepository artistSearchRepository;
	private final ArtistSearchHistoryRepository artistSearchHistoryRepository;

	@RabbitListener(queues = ArtistRabbitMqConstants.ARTIST_QUEUE)
	public void receiveArtistMessage(ArtistMessage artist) {
		if(artist == null) {
			return;
		}
		
		ArtistSearch artistSearch = artistSearchMapper.toArtistSearch(artist);
		ArtistSearch existing  = artistSearchRepository.findByArtistId(artistSearch.getArtistId());
		if(existing  != null) {
			artistSearch.setId(existing.getId());
		}
		
		artistSearchRepository.save(artistSearch);
	}
	
	@RabbitListener(queues = ArtistRabbitMqConstants.ARTIST_SEARCH_HISTORY_QUEUE)
	public void receiveArtistSearchHistoryMessage(ArtistSearchHistoryMessage artist) {
		if(artist == null) {
			return;
		}
		
		ArtistSearchHistory artistSearchHistory = artistSearchHistoryMapper.toArtistSearchHistory(artist);
		ArtistSearchHistory existing  = artistSearchHistoryRepository
				.findBySearcherArtistIdAndTargetArtistId(
						artistSearchHistory.getSearcherArtistId(), artistSearchHistory.getTargetArtistId()
				);
		if(existing  != null) {
			artistSearchHistoryRepository.deleteById(existing.getId());
		}
		
		artistSearchHistoryRepository.save(artistSearchHistory);
	}
}
