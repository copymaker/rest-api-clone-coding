package com.example.demo.event;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private LocalDateTime openEnrollmentDateTime;

    @NotNull
    private LocalDateTime closeEnrollmentDateTime;

    @NotNull
    private LocalDateTime beginEventDateTime;

    @NotNull
    private LocalDateTime endEventDateTime;

    private String location;    // (optional) 이게 없으면 온라인 모임

    @Min(0)
    private int basePrice;      // (optional)

    @Min(0)
    private int maxPrice;       // (optional)

    @Min(0)
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
