package com.github.udanton.monitoringexample.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OtherJoke {
    private String id;
    private String text;
    @Builder.Default
    private String storage = "elasticsearch";
}
