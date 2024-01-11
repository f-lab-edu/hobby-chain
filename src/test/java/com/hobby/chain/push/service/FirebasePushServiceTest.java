package com.hobby.chain.push.service;

import com.hobby.chain.push.PushType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FirebasePushServiceTest {
    private final PushService pushService;
    private final ProducerService producerService;

    @Autowired
    public FirebasePushServiceTest(PushService pushService, ProducerService producerService) {
        this.pushService = pushService;
        this.producerService = producerService;
    }

    @Test
    void 토큰_생성_테스트() {
        //given
        String userId = "1";

        //when
        pushService.setToken(userId);

        //then
        Assertions.assertThat(pushService.getToken(userId)).isNotNull();
    }

    @Test
    void 메세지_전송_테스트() {
        //given
        List<String> ids = new ArrayList<>();
        ids.add("1");

        //when
        producerService.sendMessageToQueue(ids, PushType.NEW_POST, "test");

        //then
        //RabbitMQ로 확인
    }
}