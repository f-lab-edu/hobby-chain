package com.hobby.chain.push.service;

import com.hobby.chain.push.PushType;

import java.util.List;

public interface PushService {
    public void setToken(Long userId);
    public Object getToken(long userId);
    public void deleteToken(long userId);
    public void sendPushMessage(long userId, List<Long> receiveId, PushType pushType, String pushMessage);
}
