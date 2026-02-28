package org.example.notificationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "processed_events",
uniqueConstraints = {
        @UniqueConstraint(name = "uk_event_id",columnNames = "event_id")
})
@Getter
@Setter
@NoArgsConstructor
public class ProcessedEvent {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "event_id",nullable = false)
    @NotNull
    private UUID eventId;

    @Column(name = "processed_at")
    @CreationTimestamp
    private Instant processedAt;

}
