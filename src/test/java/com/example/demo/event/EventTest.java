package com.example.demo.event;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest
    @CsvSource({
        "0, 0, true",
        "0, 100, false",
        "100, 0, false",
        "100, 200, false"
    })
    void freeTest(int basePrice, int maxPrice, boolean isFree) {
        // given
        Event event = Event.builder()
            .basePrice(basePrice)
            .maxPrice(maxPrice)
            .build();

        // when
        event.update();

        // then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    @ParameterizedTest
    @MethodSource("parameterForOfflineTest")
    void offlineTest(String location, boolean isOffline) {
        // given
        Event event = Event.builder()
            .location(location)
            .build();

        // when
        event.update();

        // then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    private static Stream<Arguments> parameterForOfflineTest() {
        return Stream.of(
            Arguments.of("강남", true),
            Arguments.of(null, false),
            Arguments.of("     ", false)
        );
    }

}