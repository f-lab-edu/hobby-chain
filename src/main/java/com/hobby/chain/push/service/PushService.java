package com.hobby.chain.push.service;

import com.hobby.chain.push.PushType;
import com.hobby.chain.push.dto.MessageDto;

import java.util.List;

public interface PushService {
    public void setToken(String userId);
    public String getToken(String userId);
    public void deleteToken(String userId);
    public void sendPushMessage(MessageDto messageDto);
}
