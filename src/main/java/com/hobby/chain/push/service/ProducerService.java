package com.hobby.chain.push.service;

import com.hobby.chain.push.PushType;
import com.hobby.chain.push.dto.MessageDto;

import java.util.List;

public interface ProducerService {
    void sendMessage(List<String> receiveIds, PushType pushType, String pushMessage);
}
