package com.ps.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

import com.ps.response.ForecastManagerConsumer;
import com.ps.response.Transaction;

@Component
public class RabbitMqListinerService implements RabbitListenerConfigurer {

	private static final Logger logger = LogManager.getLogger(RabbitMqListinerService.class);
	
	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		// TODO Auto-generated method stub
		
	}
	
	@RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void receivedMessage(ForecastManagerConsumer forecastManagerConsumer) {
		
		List<Transaction> transaction= forecastManagerConsumer.getListTransaction();
		transaction.forEach(t->{
			logger.info("Transaction List : "+t);
		});
        logger.info("User Details Received is.. " +forecastManagerConsumer );
    }

	
}
