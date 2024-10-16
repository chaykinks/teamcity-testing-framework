package org.example.teamcity.api.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import org.example.teamcity.api.annotations.Random;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseModel {
    private String id;
    @Random
    private String username;
    @Random
    private String password;
    private Roles roles;
}
