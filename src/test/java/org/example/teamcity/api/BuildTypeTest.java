package org.example.teamcity.api;

import org.apache.http.HttpStatus;
import org.example.teamcity.api.models.BuildType;
import org.example.teamcity.api.models.Project;
import org.example.teamcity.api.models.Roles;
import org.example.teamcity.api.models.User;
import org.example.teamcity.api.requests.CheckedRequests;
import org.example.teamcity.api.requests.unchecked.UncheckedBase;
import org.example.teamcity.api.spec.Specifications;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.qameta.allure.Allure.step;
import static org.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static org.example.teamcity.api.enums.Endpoint.PROJECTS;
import static org.example.teamcity.api.enums.Endpoint.USERS;
import static org.example.teamcity.api.generators.TestDataGenerator.generate;


@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }

        /*var user = generate(User.class);

        superUserCheckRequests.getRequest(USERS).create(user);
        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(user));

        var project = generate(Project.class);

        project = userCheckRequests.<Project>getRequest(PROJECTS).create(project);

        var buildType = generate(Arrays.asList(project), BuildType.class);

        userCheckRequests.getRequest(BUILD_TYPES).create(buildType);

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(buildType.getId());

        softy.assertEquals(buildType.getName(), createdBuildType.getName(), "Build type name is not correct");*/

        /*var user = generate(User.class);

        step("Create user", () -> {
            var requester = new CheckedBase<User>(Specifications.superUserAuthSpec(), Endpoint.USERS);
            requester.create(user);
        });

        var project = generate(Project.class);
        AtomicReference<String> projectId = new AtomicReference<>("");

        step("Create project by user", () -> {
            var requester = new CheckedBase<Project>(Specifications.authorizedSpec(user), Endpoint.PROJECTS);
            projectId.set(requester.create(project).getId());
        });

        var buildType = generate(BuildType.class);
        buildType.setProject(Project.builder().id(projectId.get()).locator(null).build());

        var requester = new CheckedBase<BuildType>(Specifications.authorizedSpec(user), Endpoint.BUILD_TYPES);
        AtomicReference<String> buildTypeId = new AtomicReference<>("");

        step("Create buildType for project by user", () -> {
            buildTypeId.set(requester.create(buildType).getId());
        });

        step("Check buildType was created successfully with correct data", () ->  {
            var createdBuildType = requester.read(buildTypeId.get());

            softy.assertEquals(buildType.getName(), createdBuildType.getName(), "Build type name is not correct");
        });*/


    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authorizedSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(("The build configuration / template ID \"%s\" is already used by another " +
                        "configuration or template").formatted(testData.getBuildType().getId())));
    }


        /*var user = generate(User.class);

        superUserCheckRequests.getRequest(USERS).create(user);
        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(user));

        var project = generate(Project.class);

        project = userCheckRequests.<Project>getRequest(PROJECTS).create(project);

        var buildType1 = generate(Arrays.asList(project), BuildType.class);
        var buildType2 = generate(Arrays.asList(project), BuildType.class, buildType1.getId());

        userCheckRequests.getRequest(BUILD_TYPES).create(buildType1);
        new UncheckedBase(Specifications.authorizedSpec(user), BUILD_TYPES)
                .create(buildType2)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(("The build configuration / template ID \"%s\" is already used by another " +
                        "configuration or template").formatted(buildType1.getId())));*/

        /*step("Create user");
        step("Create project by user");
        step("Create buildType1 for project by user");
        step("Create buildType2 with same id as buildType1 for project by user");
        step("Check buildType2 was not created with bad request code");*/


    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {

        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());

        new UncheckedBase(Specifications.authorizedSpec(testData.getUser()), BUILD_TYPES)
                .create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .body(Matchers.containsString(testData.getBuildType().getName()));

        /*superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authorizedSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));

        step("Create user");
        step("Create project");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create buildType for project by user (PROJECT_ADMIN)");
        step("Check buildType was created successfully");*/
    }

    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {

        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());
        var project_2 = superUserCheckRequests.<Project>getRequest(PROJECTS).create(generate(Project.class));

        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));

        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        var user_2 = generate(User.class);
        user_2.setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + project_2.getId()));

        superUserCheckRequests.<User>getRequest(USERS).create(user_2);

        new UncheckedBase(Specifications.authorizedSpec(user_2), BUILD_TYPES)
                .create(testData.getBuildType())
                        .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                        .body(Matchers.containsString(("You do not have enough permissions to edit project with id: " +
                                "%s").formatted(testData.getProject().getId())));


        step("Create user1");
        step("Create project1");
        step("Grant user1 PROJECT_ADMIN role in project1");

        step("Create user2");
        step("Create project2");
        step("Grant user2 PROJECT_ADMIN role in project2");

        step("Create buildType for project1 by user2");
        step("Check buildType was not created with forbidden code");
    }
}
