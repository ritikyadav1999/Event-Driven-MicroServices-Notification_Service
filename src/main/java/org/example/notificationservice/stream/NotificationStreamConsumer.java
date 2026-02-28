package org.example.notificationservice.stream;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class NotificationStreamConsumer {

    private final RedisTemplate<String, Object> redisTemplate;
    private final NotificationService notificationService;

    private static final String STREAM = "order-events";
    private static final String GROUP = "notification-group";
    private final String CONSUMER = UUID.randomUUID().toString();

    @PostConstruct
    public void start() {
        Executors.newSingleThreadExecutor().submit(this::listen);
    }

    private void listen() {

        while (true) {

            List<MapRecord<String, Object, Object>> messages =
                    redisTemplate.opsForStream().read(
                            Consumer.from(GROUP, CONSUMER),
                            StreamReadOptions.empty().block(Duration.ofSeconds(5)),
                            StreamOffset.create(STREAM, ReadOffset.lastConsumed())
                    );

            if (messages == null) continue;

            for (MapRecord<String, Object, Object> message : messages) {

                try {
                    notificationService.process(message);

                    redisTemplate.opsForStream()
                            .acknowledge(STREAM, GROUP, message.getId());

                } catch (Exception e) {
                    // Do NOT ack on failure
                    e.printStackTrace();
                }
            }
        }
    }
}