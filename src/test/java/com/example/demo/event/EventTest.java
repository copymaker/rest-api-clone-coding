package com.example.demo.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void javaBeanTest() {
        // given
        String name = "Event";
        String description = "Spring";

        // when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }


    @Test
    void builderTest() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with Spring")
                .build();

        assertThat(event).isNotNull();
    }

    @Test
    void freeTest() {
        // given
        Event event = Event.builder()
            .basePrice(0)
            .maxPrice(0)
            .build();

        // when
        event.update();

        // then
        assertThat(event.isFree()).isTrue();

        // given
        event = Event.builder()
            .basePrice(0)
            .maxPrice(100)
            .build();

        // when
        event.update();

        // then
        assertThat(event.isFree()).isFalse();
    }

    @Test
    void offlineTest() {
        // given
        Event event = Event.builder()
            .location("강남역 네이버 D2 스타트업 팩토리")
            .build();

        // when
        event.update();

        // then
        assertThat(event.isOffline()).isTrue();

        // given
        event = Event.builder().build();

        // when
        event.update();

        // then
        assertThat(event.isOffline()).isFalse();
    }

}