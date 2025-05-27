package org.example.lab11_springboot.controller;

import org.example.lab11_springboot.client.GraphColoring;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/colors")
public class ColorController {
    private final GraphColoring graphColoring;

    public ColorController(GraphColoring graphColoring) {
        this.graphColoring = graphColoring;
    }

    @GetMapping
    public Map<String, String> getCountryColors() {
        return graphColoring.colorGraph();
    }
}
