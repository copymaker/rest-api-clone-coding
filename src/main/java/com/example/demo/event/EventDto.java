package com.example.demo.event;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDto {

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

    @Builder
    public EventDto(String name, String description, LocalDateTime openEnrollmentDateTime,
        LocalDateTime closeEnrollmentDateTime, LocalDateTime beginEventDateTime,
        LocalDateTime endEventDateTime, String location, int basePrice, int maxPrice,
        int limitOfEnrollment) {
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
    }
}
