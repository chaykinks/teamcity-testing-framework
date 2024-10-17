package org.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.teamcity.api.annotations.Parameterizable;
import org.example.teamcity.api.enums.RoleId;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends BaseModel {
    @Parameterizable
    @Builder.Default
    private RoleId roleId = RoleId.SYSTEM_ADMIN;
    @Parameterizable
    @Builder.Default
    private String scope = "g";
}
