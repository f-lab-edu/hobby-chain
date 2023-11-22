package com.hobby.chain.push.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.hobby.chain.push.PushType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FirebasePushService implements PushService{
    private final RedisTemplate<String, Object> redisTemplate;

    public FirebasePushService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setToken(String userId) {
        redisTemplate.opsForValue().set(userId, getAccessToken());
        redisTemplate.expire(userId, Duration.ofHours(1L));
    }

    @Override
    public String getToken(String userId) {
        if(!redisTemplate.hasKey(userId)){
            setToken(userId);
        }

        return String.valueOf(redisTemplate.opsForValue().get(userId));
    }

    @Override
    public void deleteToken(String userId) {
        redisTemplate.delete(userId);
    }

    @Override
    public void sendPushMessage(List<String> receiveIds, PushType pushType, String pushMessage) {
        receiveIds.stream()
                        .map(receiveId -> buildMessage(receiveId, pushType, pushMessage)
                        .thenComposeAsync(message -> CompletableFuture.runAsync(() -> FirebaseMessaging.getInstance().sendAsync(message))));
    }

    @Async
    public CompletableFuture<Message> buildMessage(String receiveId, PushType pushType, String pushMessage){
        return CompletableFuture.completedFuture(Message.builder()
                .putData("title", pushType.getType())
                .putData("body", pushMessage)
                .putData("createTime", String.valueOf(LocalTime.now()))
                .setToken(getToken(receiveId)).build());
    }

    private Object getAccessToken(){
        String firebaseConfigPath = "hobby-chain-firebase-adminsdk.json";

        try {
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform",
                                          "https://www.googleapis.com/auth/firebase.messaging"));

            credentials.refreshIfExpired();
            return credentials.getAccessToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
