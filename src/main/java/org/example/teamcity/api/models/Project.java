package org.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.teamcity.api.annotations.Random;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseModel {
    private String id;
    @Random
    private String name;
    @Builder.Default
    private String locator = "_Root";
}
