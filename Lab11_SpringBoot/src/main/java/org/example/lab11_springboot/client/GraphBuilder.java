package org.example.lab11_springboot.client;

import org.example.lab11_springboot.model.Border;
import org.example.lab11_springboot.model.Country;
import org.example.lab11_springboot.repository.BorderRepository;
import org.example.lab11_springboot.repository.CountryRepository;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GraphBuilder {
    private final CountryRepository countryRepo;
    private final BorderRepository borderRepo;

    public GraphBuilder(CountryRepository countryRepo, BorderRepository borderRepo) {
        this.countryRepo = countryRepo;
        this.borderRepo = borderRepo;
    }

    public Graph<String, DefaultEdge> buildGraph() {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        List<Country> countries = countryRepo.findByContinentId(1);
        Set<String> countryCodes = countries.stream().
                map(Country::getCode)
                .collect(Collectors.toSet());

        for (String code : countryCodes) {
            graph.addVertex(code);
        }

        for(Border border : borderRepo.findAll()) {
            String countryCode = border.getCountryCode();
            String neighborCode = border.getNeighborCode();

            if((countryCodes.contains(countryCode)) && (countryCodes.contains(neighborCode))) {
                if((countryCode.compareTo(neighborCode) < 0) && (!graph.containsEdge(countryCode, neighborCode))) {
                    graph.addEdge(countryCode, neighborCode);
                }
            }
        }

        return graph;
    }

    public void GraphReview(){
        Graph<String, DefaultEdge> graph = buildGraph();

        System.out.println("Noduri in graf: " + graph.vertexSet().size());
        System.out.println("Muchii in graf: " + graph.edgeSet().size());

        for (String country : graph.vertexSet()) {
            Set<DefaultEdge> edges = graph.edgesOf(country);
            Set<String> neighbors = edges.stream()
                    .map(e -> {
                        String src = graph.getEdgeSource(e);
                        String tgt = graph.getEdgeTarget(e);
                        return src.equals(country) ? tgt : src;
                    })
                    .collect(Collectors.toSet());

            System.out.println(country + " are vecini: " + neighbors);
        }
    }
}
