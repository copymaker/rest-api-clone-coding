package com.example.demo.event;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.common.BaseControllerTest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class EventControllerTest extends BaseControllerTest {

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("정상적으로 이벤트를 생성하는 테스트")
    void createEventTest() throws Exception {
        EventDto eventDto = EventDto.builder()
            .name("Spring")
            .description("REST API Development with Spring")
            .openEnrollmentDateTime(LocalDateTime.of(2020, 2, 23, 14, 21))
            .closeEnrollmentDateTime(LocalDateTime.of(2020, 2, 24, 14, 21))
            .beginEventDateTime(LocalDateTime.of(2020, 2, 25, 14, 21))
            .endEventDateTime(LocalDateTime.of(2020, 2, 26, 14, 21))
            .basePrice(100)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남역 D2 스타텁 팩토리")
            .build();

        mockMvc
            .perform(
                post("/api/events")
                    .characterEncoding(StandardCharsets.UTF_8.name())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().exists(HttpHeaders.LOCATION))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, Matchers.containsString(MediaTypes.HAL_JSON_VALUE)))
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("free").value(false))
            .andExpect(jsonPath("offline").value(true))
            .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
            .andDo(document("create-event",
                links(
                    linkWithRel("self").description("link to self"),
                    linkWithRel("query-events").description("link to query events"),
                    linkWithRel("update-event").description("link to update an existing event"),
                    linkWithRel("profile").description("link to update an existing event")
                ),
                requestHeaders(
                    headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                ),
                requestFields(
                    fieldWithPath("name").description("Name of new event"),
                    fieldWithPath("description").description("description of new event"),
                    fieldWithPath("openEnrollmentDateTime").description("date time of begin of new event"),
                    fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                    fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                    fieldWithPath("endEventDateTime").description("date time of end of new event"),
                    fieldWithPath("location").description("location of new event"),
                    fieldWithPath("basePrice").description("base price of new event"),
                    fieldWithPath("maxPrice").description("max price of new event"),
                    fieldWithPath("limitOfEnrollment").description("limit of enrollment")
                ),
                responseHeaders(
                    headerWithName(HttpHeaders.LOCATION).description("Location header"),
                    headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                ),
                responseFields(
                    fieldWithPath("id").description("identifier of new event"),
                    fieldWithPath("name").description("Name of new event"),
                    fieldWithPath("description").description("description of new event"),
                    fieldWithPath("openEnrollmentDateTime").description("date time of begin of new event"),
                    fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                    fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                    fieldWithPath("endEventDateTime").description("date time of end of new event"),
                    fieldWithPath("location").description("location of new event"),
                    fieldWithPath("basePrice").description("base price of new event"),
                    fieldWithPath("maxPrice").description("max price of new event"),
                    fieldWithPath("limitOfEnrollment").description("limit of enrollment"),
                    fieldWithPath("free").description("it tells if this event is free or not"),
                    fieldWithPath("offline").description("it tells if this event is offline event or not"),
                    fieldWithPath("eventStatus").description("event status"),
                    fieldWithPath("manager").description("manager"),
                    fieldWithPath("_links.self.href").description("link to self"),
                    fieldWithPath("_links.query-events.href").description("link to query event list"),
                    fieldWithPath("_links.update-event.href").description("link to update existing event"),
                    fieldWithPath("_links.profile.href").description("link to profile")
                )
            ));
    }

    @Test
    @DisplayName("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
    void createEvent_Bad_Request() throws Exception {
        Event event = Event.builder()
            .id(100L)
            .name("Spring")
            .description("REST API Development with Spring")
            .openEnrollmentDateTime(LocalDateTime.of(2020, 2, 23, 14, 21))
            .closeEnrollmentDateTime(LocalDateTime.of(2020, 2, 24, 14, 21))
            .beginEventDateTime(LocalDateTime.of(2020, 2, 25, 14, 21))
            .endEventDateTime(LocalDateTime.of(2020, 2, 26, 14, 21))
            .basePrice(100)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남역 D2 스타텁 팩토리")
            .free(true)
            .offline(false)
            .eventStatus(EventStatus.PUBLISHED)
            .build();

        mockMvc
            .perform(
                post("/api/events")
                    .characterEncoding(StandardCharsets.UTF_8.name())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc
            .perform(
                post("/api/events")
                    .characterEncoding(StandardCharsets.UTF_8.name())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
    void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
            .name("Spring")
            .description("REST API Development with Spring")
            .openEnrollmentDateTime(LocalDateTime.of(2020, 2, 23, 14, 21))
            .closeEnrollmentDateTime(LocalDateTime.of(2020, 2, 24, 14, 21))
            .beginEventDateTime(LocalDateTime.of(2020, 2, 25, 14, 21))
            .endEventDateTime(LocalDateTime.of(2020, 2, 26, 14, 21))
            .basePrice(10000)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남역 D2 스타텁 팩토리")
            .build();

        mockMvc
            .perform(
                post("/api/events")
                    .characterEncoding(StandardCharsets.UTF_8.name())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("content[0].objectName").exists())
            .andExpect(jsonPath("content[0].defaultMessage").exists())
            .andExpect(jsonPath("content[0].code").exists())
            .andExpect(jsonPath("_links.index").exists());
    }

    @Test
    @DisplayName("기존의 이벤트를 하나 조회하기")
    void getEventTest() throws Exception {
        // given
        Event event = generateEvent(100);

        // when & then
        mockMvc
            .perform(get("/api/events/{id}", event.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(event.getId()))
            .andExpect(jsonPath("name").value(event.getName()))
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.profile").exists())
            .andDo(document("get-an-event"));
    }

    @Test
    @DisplayName("없는 이벤트를 조회했을 때 404 응답받기")
    void getEvent404() throws Exception {
        mockMvc.perform(get("/api/events/-1"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("30개의 이벤트를 10개씩 두번째 페이지 조회하기")
    void queryEvents() throws Exception {
        // given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // when & then
        mockMvc
            .perform(
                get("/api/events")
                    .param("page", "1")
                    .param("size", "10")
                    .param("sort", "name,DESC"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("page").exists())
            .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.profile").exists())
            .andDo(document("query-events"));
    }

    @Test
    @DisplayName("이벤트를 정상적으로 수정하기")
    void updateEvent() throws Exception {
        // given
        Event event = generateEvent(200);

        EventDto eventDto = eventMapper.toDto(event);
        String expectedEventName = "Updated Event";
        eventDto.setName(expectedEventName);

        // when & then
        mockMvc
            .perform(
                put("/api/events/{id}", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(expectedEventName))
            .andExpect(jsonPath("_links.self").exists())
            .andDo(document("update-event"));
    }

    @Test
    @DisplayName("입력값이 비어있는 경우 이벤트 수정 실패")
    void updateEvent400_empty() throws Exception {
        // given
        Event event = generateEvent(200);

        EventDto eventDto = new EventDto();

        // when & then
        mockMvc
            .perform(
                put("/api/events/{id}", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력값이 잘못된 경우에 이벤트 수정 실패")
    void updateEvent400_wrong() throws Exception {
        // given
        Event event = generateEvent(200);

        EventDto eventDto = eventMapper.toDto(event);
        eventDto.setBasePrice(10000);
        eventDto.setMaxPrice(100);

        // when & then
        mockMvc
            .perform(
                put("/api/events/{id}", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 이벤트 수정 실패")
    void updateEvent404() throws Exception {
        // given
        Event event = generateEvent(200);

        EventDto eventDto = eventMapper.toDto(event);

        // when & then
        mockMvc
            .perform(
                put("/api/events/{id}", -1111)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(eventDto)))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    private Event generateEvent(int index) {
        Event event = Event.builder()
            .name("event" + index)
            .description("test event")
            .openEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
            .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
            .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
            .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
            .basePrice(100)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남역 D2 스타텁 팩토리")
            .free(false)
            .offline(true)
            .eventStatus(EventStatus.DRAFT)
            .build();

        return eventRepository.save(event);
    }
}
