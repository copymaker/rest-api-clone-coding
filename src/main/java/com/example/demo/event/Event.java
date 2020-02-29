package com.example.demo.event;

import com.example.demo.account.Account;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
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

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne
    private Account manager;

    public void update() {
        // Update free
        this.free = basePrice == 0 && maxPrice == 0;

        // Update location
        this.offline = location != null && !StringUtils.trimWhitespace(location).isEmpty();
    }
}
