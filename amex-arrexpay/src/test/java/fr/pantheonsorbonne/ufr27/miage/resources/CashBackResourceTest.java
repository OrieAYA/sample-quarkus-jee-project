package fr.pantheonsorbonne.ufr27.miage.resources;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class CashBackResourceTest {

    @Test
    void should_return_status_200() {
        Response response = given()
                .when()
                .get("/cashback/456")
                .then()
                .statusCode(200)
                .extract()
                .response();

        response.as(Double.class);
        assertEquals(28, response.as(Double.class));
    }

    @Test
    void should_return_status_404_when_client_doesnt_exist() {
        given()
                .when()
                .get("/cashback/400")
                .then()
                .statusCode(404);
    }
}