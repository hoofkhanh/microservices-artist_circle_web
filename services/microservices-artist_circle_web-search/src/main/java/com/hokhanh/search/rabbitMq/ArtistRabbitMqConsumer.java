package com.hokhanh.search.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hokhanh.common.constant.ArtistRabbitMqConstants;
import com.hokhanh.common.rabbitMq.dto.ArtistMessage;
import com.hokhanh.search.mapper.ArtistElasticSearchMapper;
import com.hokhanh.search.model.ArtistElasticSearch;
import com.hokhanh.search.repository.ArtistElasticsearchRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArtistRabbitMqConsumer {
	private final ArtistElasticSearchMapper mapper;
	private final ArtistElasticsearchRepository repository;

	@RabbitListener(queues = ArtistRabbitMqConstants.QUEUE)
	public void receiveArtistMessage(ArtistMessage artist) {
		if(artist == null) {
			return;
		}
		System.out.println("vô");
		ArtistElasticSearch artistElasticSearch = mapper.toArtistElasticSearch(artist, null);
		ArtistElasticSearch existing  = repository.findByArtistId(artistElasticSearch.getArtistId());
		if(existing  != null) {
			artistElasticSearch.setId(existing .getId());
		}
		
		repository.save(artistElasticSearch);
	}
}
