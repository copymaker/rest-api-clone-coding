package com.example.demo.index;

import com.example.demo.event.EventController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/api")
    public RepresentationModel index() {
        RepresentationModel index = new RepresentationModel();
        index.add(WebMvcLinkBuilder.linkTo(EventController.class).withRel("events"));
        return index;
    }

}
