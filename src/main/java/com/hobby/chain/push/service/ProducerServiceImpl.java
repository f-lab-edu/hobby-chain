package com.hobby.chain.push.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hobby.chain.push.PushType;
import com.hobby.chain.push.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ProducerServiceImpl implements ProducerService{
    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;
    @Value("${spring.rabbitmq.dlq.exchange}")
    private String dlqExchange;
    @Value("${spring.rabbitmq.dlq.routing-key}")
    private String dlqRoutingKey;

    private final RabbitTemplate rabbitTemplate;
    private final PushService pushService;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate, PushService pushService) {
        this.rabbitTemplate = rabbitTemplate;
        this.pushService = pushService;
    }

    @Override
    public void sendMessageToQueue(List<String> receiveIds, PushType pushType, String pushMessage) {
        for (String receiveId : receiveIds) {
            MessageDto messageDto = buildDto(receiveId, pushType, pushMessage);
            try{
                rabbitTemplate.convertAndSend(exchange, routingKey, messageDto); //일반 message queue
            } catch (RuntimeException re){
                rabbitTemplate.convertAndSend(dlqExchange, dlqRoutingKey, messageDto); //queue에 전송 실패 시 dead letter queue
            }
        }
    }

    private MessageDto buildDto(String receiveId, PushType pushType, String pushMessage){
        return MessageDto.builder()
                .title(pushType.getType())
                .body(pushMessage)
                .createTime(String.valueOf(LocalTime.now()))
                .token(pushService.getToken(receiveId))
                .build();
    }
}
