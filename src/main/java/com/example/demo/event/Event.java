package com.example.demo.event;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private LocalDateTime openEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;    // (optional) 이게 없으면 온라인 모임
    private int basePrice;      // (optional)
    private int maxPrice;       // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;

    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    @Builder
    public Event(Long id,
                 String name,
                 String description,
                 LocalDateTime openEnrollmentDateTime,
                 LocalDateTime closeEnrollmentDateTime,
                 LocalDateTime beginEventDateTime,
                 LocalDateTime endEventDateTime,
                 String location,
                 int basePrice,
                 int maxPrice,
                 int limitOfEnrollment,
                 boolean offline,
                 boolean free,
                 EventStatus eventStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.openEnrollmentDateTime = openEnrollmentDateTime;
        this.closeEnrollmentDateTime = closeEnrollmentDateTime;
        this.beginEventDateTime = beginEventDateTime;
        this.endEventDateTime = endEventDateTime;
        this.location = location;
        this.basePrice = basePrice;
        this.maxPrice = maxPrice;
        this.limitOfEnrollment = limitOfEnrollment;
        this.offline = offline;
        this.free = free;
        this.eventStatus = eventStatus;
    }
}
