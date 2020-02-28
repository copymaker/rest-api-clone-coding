package com.example.demo.event;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class EventEntityModel extends EntityModel<Event> {

    public EventEntityModel(Event event, Link... links) {
        super(event, links);
        add(WebMvcLinkBuilder.linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}
