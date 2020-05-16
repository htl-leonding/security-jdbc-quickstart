package at.htl.security.jdbc;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class ResourcesTest {

    @Test
    void testPublicResource() {

        given()
                .when().get("/api/public")
                .then()
                .statusCode(200)
                .body(containsString("public"));
    }

    @Test
    void testAdminResourceWOCredentials() {

        given()
                .when().get("/api/admin")
                .then()
                .statusCode(401);
    }

    @Test
    void testAdminResourceWCredentials() {

        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .when().get("/api/admin")
                .then()
                .statusCode(200)
                .body(equalTo("admin"));
    }

    @Test
    void testUserResource() {

        given()
                .auth()
                .preemptive()
                .basic("user", "user")
                .when().get("/api/users/me")
                .then()
                .statusCode(200)
                .body(equalTo("user"));
    }

/*
@Test
    public void testForbiddenEndpoint() {
        given()
                .when()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("user:passwd".getBytes()))
                .get("/hello/forbidden")
                .then()
                .statusCode(403)
                .header("Content-Type", "text/plain")
                .body(is("Forbidden"));
    }
 */
}