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

    private final RabbitTemplate rabbitTemplate;
    private final PushService pushService;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate, PushService pushService) {
        this.rabbitTemplate = rabbitTemplate;
        this.pushService = pushService;
    }

    @Override
    public void sendMessageToQueue(List<String> receiveIds, PushType pushType, String pushMessageo) {
        for (String receiveId : receiveIds) {
            MessageDto messageDto = buildDto(receiveId, pushType, pushMessageo);
            rabbitTemplate.convertAndSend(exchange, routingKey, messageDto);
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
