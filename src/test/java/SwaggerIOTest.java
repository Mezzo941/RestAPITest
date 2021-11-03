import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;


public class SwaggerIOTest {

    @Test
    public void createPetTest() {
        given()
                .body("{\n" +
                        "  \"id\": 110110,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"buba\"\n" +
                        "  },\n" +
                        "  \"name\": \"parrot\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}").
                header("Content-Type", "application/json")
                .log().all()
                .when()
                .post("https://petstore.swagger.io/v2/pet").
                then()
                .log().all()
                .statusCode(200)
                .body("category.name", equalTo("buba"));

    }

    @Test
    public void findPetByStatusTest() {
        String[] status = {"available", "pending", "sold"};
        String currentStatus = status[2];
        when().
                get(String.format("https://petstore.swagger.io/v2/pet/findByStatus?status=%s", currentStatus))
                .then()
                .log().all()
                .statusCode(200)
                .body("status[0]", equalTo(currentStatus));
    }

    @Test
    public void findPetByIdTest() {
        String petId = "110110";
        when().
                get(String.format("https://petstore.swagger.io/v2/pet/%s", petId.trim()))
                .then()
                .log().all()
                .statusCode(200)
                .body("category.name", equalTo("buba"));
    }

    @Test
    public void updatePetTest() {
        given()
                .body("{\n" +
                        "  \"id\": 110110,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"lupa\"\n" +
                        "  },\n" +
                        "  \"name\": \"rat\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}").
                header("Content-Type", "application/json")
                .log().all()
                .when()
                .put("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("category.name", equalTo("lupa"),
                        "name", equalTo("rat"));

    }

    @Test()
    public void updatePetWithFormDataTest() {
        int id = 110110;
        String name = "bird";
        String status = "sold";
        given()
                .body(String.format("{\n" +
                        "  \"id\": %s,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"lupa\" \n" +
                        "  },\n" +
                        "  \"name\": \"%s\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"%s\" \n" +
                        "}", id, name, status))
                .header("Content-Type", "application/json")
                .log().all()
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("category.name", equalTo("lupa"),
                        "name", equalTo("bird"));

    }

    @Test
    public void deletePetTest() {
        String petId = "123";
        when().
                delete(String.format("https://petstore.swagger.io/v2/pet/%s", petId.trim()))
                .then()
                .statusCode(404)
                .log().all();
    }


}
