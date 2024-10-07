package org.example.teamcity.api;

import org.example.teamcity.api.requests.CheckedRequests;
import org.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {
    protected SoftAssert softy;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserAuthSpec());

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softy = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
    }
}
