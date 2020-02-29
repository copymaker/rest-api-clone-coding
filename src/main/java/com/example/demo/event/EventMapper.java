package com.example.demo.event;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventDto eventDto);

    EventDto toDto(Event event);
}
