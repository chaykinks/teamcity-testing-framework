package org.example.teamcity.api.enums;

import org.example.teamcity.api.models.BaseModel;
import org.example.teamcity.api.models.BuildType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.teamcity.api.models.Project;
import org.example.teamcity.api.models.User;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECTS("/app/rest/projects",Project.class),
    USERS("/app/rest/users", User.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass;
}