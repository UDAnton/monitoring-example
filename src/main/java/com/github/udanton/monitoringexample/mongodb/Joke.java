package com.github.udanton.monitoringexample.mongodb;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "jokes")
@Builder(toBuilder = true)
public class Joke {
    @Id
    private String id;
    private String text;
    @Builder.Default
    private String storage = "mongoDB";
}
