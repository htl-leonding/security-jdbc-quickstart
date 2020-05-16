package at.htl.security.jdbc;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResourcesTest {

    @Test
    @Order(1)
    void testPublicResource() {

        given()
                .when().get("/api/public")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(containsString("public"));
    }

    @Test
    @Order(2)
    void testAdminResourceWOCredentials() {

        given()
                .when().get("/api/admin")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @Order(3)
    void testAdminResourceWCredentials() {

        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .when().get("/api/admin")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("admin"));
    }

    @Test
    @Order(4)
    void testUserResource() {

        given()
                .auth()
                .preemptive()
                .basic("user", "user")
                .when().get("/api/users/me")
                .then()
                .statusCode(HttpStatus.SC_OK)
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