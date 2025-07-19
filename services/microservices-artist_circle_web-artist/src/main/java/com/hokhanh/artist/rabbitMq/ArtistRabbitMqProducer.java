package com.hokhanh.artist.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.hokhanh.common.constant.ArtistRabbitMqConstants;
import com.hokhanh.common.rabbitMq.dto.ArtistMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArtistRabbitMqProducer {
	
	private final RabbitTemplate rabbitTemplate;

	public void sendArtistMessage(ArtistMessage artit) {
	    rabbitTemplate.convertAndSend(
	        ArtistRabbitMqConstants.EXCHANGE,
	        ArtistRabbitMqConstants.ROUTING_KEY,
	        artit
	    );
	}

		
}
