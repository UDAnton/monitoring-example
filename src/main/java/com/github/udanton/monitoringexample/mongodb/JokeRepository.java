package com.github.udanton.monitoringexample.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JokeRepository extends MongoRepository<Joke, String> {
}
