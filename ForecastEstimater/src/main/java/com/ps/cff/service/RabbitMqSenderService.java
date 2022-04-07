package com.ps.cff.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ps.cff.entity.ForecastManager;
/**
 * 
 * @author Hemant
 *
 */
@Service
public class RabbitMqSenderService {

	private RabbitTemplate rabbitTemplate;
    @Autowired
    public RabbitMqSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;
    
    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingkey;
    
    /**
     * 
     * @param forecastManager
     */
    public void send(ForecastManager forecastManager){
        rabbitTemplate.convertAndSend(exchange,routingkey,forecastManager);
    }

}
