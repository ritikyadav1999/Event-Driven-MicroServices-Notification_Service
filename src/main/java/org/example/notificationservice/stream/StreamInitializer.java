package org.example.notificationservice.stream;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamInitializer {


    private final RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void init(){
        try{
            redisTemplate.opsForStream().createGroup("order-events",
                    ReadOffset.latest(),
                    "notification-group"
                    );
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}
