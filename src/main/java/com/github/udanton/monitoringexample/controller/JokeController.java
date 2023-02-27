package com.github.udanton.monitoringexample.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.github.udanton.monitoringexample.elasticsearch.OtherJoke;
import com.github.udanton.monitoringexample.mongodb.Joke;
import com.github.udanton.monitoringexample.mongodb.JokeRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/jokes")
@AllArgsConstructor
public class JokeController {

    private final JokeRepository jokeRepository;
    private final ElasticsearchClient elasticsearchClient;
    private final Map<String, ArrayList<String>> ids = Map.of(
        "MongoDB", new ArrayList<>(),
        "Elasticsearch", new ArrayList<>()
    );

    @GetMapping("/ids")
    public Map<String, ArrayList<String>> getAllIds() {
        return ids;
    }

    @PostMapping("/mongodb")
    @Timed(value = "JokeController.createJokeInMongoDB")
    @Counted(value = "JokeController.createJokeInMongoDB")
    public JokeResponse createJokeInMongoDB(@RequestBody JokeRequest jokeRequest) {
        Joke joke = Joke.builder()
            .id(UUID.randomUUID().toString())
            .text(jokeRequest.getText())
            .build();
        jokeRepository.save(joke);
        ids.get("MongoDB").add(joke.getId());
        return JokeResponse.builder()
            .id(joke.getId())
            .text(joke.getText())
            .build();
    }

    @GetMapping("/mongodb/{id}")
    @Timed(value = "JokeController.getByIdFromMongoId")
    @Counted(value = "JokeController.getByIdFromMongoId")
    public JokeResponse getByIdFromMongoId(@PathVariable String id) {
        return jokeRepository.findById(id)
            .map(joke -> JokeResponse.builder()
                .id(joke.getId())
                .text(joke.getText())
                .build())
            .orElse(JokeResponse.builder().build());
    }

    @PostMapping("/elasticsearch")
    @Timed(value = "JokeController.createJokeInElasticSearch")
    @Counted(value = "JokeController.createJokeInElasticSearch")
    public JokeResponse createJokeInElasticSearch(@RequestBody JokeRequest jokeRequest) throws IOException {
        OtherJoke joke = OtherJoke.builder()
            .id(UUID.randomUUID().toString())
            .text(jokeRequest.getText())
            .build();
        elasticsearchClient.index(i -> i.index("jokes").id(joke.getId()).document(joke));
        ids.get("Elasticsearch").add(joke.getId());
        return JokeResponse.builder()
            .id(joke.getId())
            .text(joke.getText())
            .build();
    }

    @GetMapping("/elasticsearch/{id}")
    @Timed(value = "JokeController.getByIdFromElasticsearch")
    @Counted(value = "JokeController.getByIdFromElasticsearch")
    public JokeResponse getByIdFromElasticsearch(@PathVariable String id) throws IOException {
        Optional<OtherJoke> possibleJoke = Optional.ofNullable(elasticsearchClient.get(g -> g.index("jokes").id(id), OtherJoke.class).source());
        if (possibleJoke.isPresent()) {
            OtherJoke otherJoke = possibleJoke.get();
            return JokeResponse.builder()
                .id(otherJoke.getId())
                .text(otherJoke.getText())
                .build();
        }
        return JokeResponse.builder().build();
    }

}
