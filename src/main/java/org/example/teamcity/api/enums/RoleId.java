package org.example.teamcity.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleId {

    PROJECT_ADMIN("PROJECT_ADMIN"),
    PROJECT_VIEWER("PROJECT_VIEWER"),
    PROJECT_DEVELOPER("PROJECT_DEVELOPER"),
    AGENT_MANAGER("AGENT_MANAGER"),
    TOOLS_INTEGRATION("TOOLS_INTEGRATION"),
    SYSTEM_ADMIN("SYSTEM_ADMIN");

    public final String roleId;
}

