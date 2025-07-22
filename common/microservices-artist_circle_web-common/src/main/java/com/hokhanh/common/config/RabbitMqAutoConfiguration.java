package com.hokhanh.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hokhanh.common.rabbitMq.ArtistRabbitMqConstants;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
public class RabbitMqAutoConfiguration {
	

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(ArtistRabbitMqConstants.EXCHANGE);
    }

    @Bean
    Queue artistQueue() {
        return new Queue(ArtistRabbitMqConstants.ARTIST_QUEUE, true); 
    }
    
    @Bean
    Queue artistSearchHistoryQueue() {
        return new Queue(ArtistRabbitMqConstants.ARTIST_SEARCH_HISTORY_QUEUE, true); 
    }

    @Bean
    Binding bindingArtistCreateQueue(Queue artistQueue, DirectExchange exchange) {
        return BindingBuilder.bind(artistQueue).to(exchange).with(ArtistRabbitMqConstants.ARTIST_ROUTING_KEY);
    }
    
    @Bean
    Binding bindingArtistSearchHistoryCreateQueue(Queue artistSearchHistoryQueue, DirectExchange exchange) {
        return BindingBuilder.bind(artistSearchHistoryQueue).to(exchange).with(ArtistRabbitMqConstants.ARTIST_SEARCH_HISTORY_ROUTING_KEY);
    }
    
    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
