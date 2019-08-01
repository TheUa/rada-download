package com.example.radadownload.rest;

import com.example.radadownload.model.DeputyPresence;
import com.example.radadownload.service.DeputiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("deputies")
public class DeputiesController {

    private DeputiesService deputiesService;

    @Autowired
    DeputiesController(@Qualifier("deputiesServiceImpl") DeputiesService deputiesService) {
     this.deputiesService = deputiesService;
    }

    @GetMapping("/")
    public List<DeputyPresence> getDeputiesPresence() {
        return deputiesService.getDeputiesPresence();
    }

}
