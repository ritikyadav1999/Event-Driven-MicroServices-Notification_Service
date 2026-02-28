package org.example.notificationservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.notificationservice.entity.ProcessedEvent;
import org.example.notificationservice.repository.ProcessedEventRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ProcessedEventRepo processedEventRepo;

    @Transactional
    public void  process(MapRecord<String,Object,Object> message) {
        String eventIdStr = (String) message.getValue().get("eventId");
        UUID eventId = UUID.fromString(eventIdStr);

        ProcessedEvent processedEvent = new ProcessedEvent();
        processedEvent.setEventId(eventId);

        try{
            processedEventRepo.save(processedEvent);
        }
        catch (DataIntegrityViolationException ex){
            return;
        }

        System.out.println("Sending notification for the event: " +  eventId);

    }



}
