package com.hokhanh.artist.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.hokhanh.common.rabbitMq.ArtistRabbitMqConstants;
import com.hokhanh.common.rabbitMq.dto.ArtistMessage;
import com.hokhanh.common.rabbitMq.dto.ArtistSearchHistoryMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArtistRabbitMqProducer {
	
	private final RabbitTemplate rabbitTemplate;

	public void sendArtistMessage(ArtistMessage artist) {
	    rabbitTemplate.convertAndSend(
	        ArtistRabbitMqConstants.EXCHANGE,
	        ArtistRabbitMqConstants.ARTIST_ROUTING_KEY,
	        artist
	    );
	}
	
	public void sendArtistSearchHistoryMessage(ArtistSearchHistoryMessage artist) {
	    rabbitTemplate.convertAndSend(
	        ArtistRabbitMqConstants.EXCHANGE,
	        ArtistRabbitMqConstants.ARTIST_SEARCH_HISTORY_ROUTING_KEY,
	        artist
	    );
	}

		
}
