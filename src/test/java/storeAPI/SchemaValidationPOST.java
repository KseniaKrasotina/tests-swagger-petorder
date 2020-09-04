package storeAPI;

import Utils.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class SchemaValidationPOST {

    @Test
    @DisplayName("2.1. Schema Validation. Check required fields. Correct request")
    public void testCorrectPayload()
    {
        OrderDTO order = new OrderDTO(0, 0, 0, "2020-09-02T09:47:32.334Z", OrderStatus.placed, true);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(order.toJson())
                .when()
                .post(Config.BASE_URL+"store/order");

         response.then().assertThat().body(matchesJsonSchemaInClasspath("response-schema-200.json"));
    }

    @ParameterizedTest
    @MethodSource("provideStatusForComplete")
    @DisplayName("2.2. Check that data in Response is correct. Pairwise data in Payload")
    void testPairweisePayload(OrderStatus status, boolean complete) {

        int id = 0;
        int petId = 1;
        int quantity = 0;
        String shipDate = "2020-09-02T09:47:32.334Z";
        OrderDTO order = new OrderDTO(id, petId, quantity, shipDate, status, complete);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(order.toJson())
                .when()
                .post(Config.BASE_URL+"store/order")
                .then()
                    .body("status", equalTo(status.name()))
                    .body("complete", equalTo(complete))
                    .body("shipDate", equalTo("2020-09-02T09:47:32.334+0000"))
                    .body("petId", equalTo(petId))
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization")
                    .header("Transfer-Encoding", "chunked")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    }

    //Если данных большое множество, целесообразно вместо этого подхода использовать массивы и делать вложенные циклы
    //When we have a lot of data, better to use Arrays and nested loops
    private static Stream<Arguments> provideStatusForComplete() {
        return Stream.of(
                Arguments.of(OrderStatus.appoved, true),
                Arguments.of(OrderStatus.delivered, true),
                Arguments.of(OrderStatus.placed, true),
                Arguments.of(OrderStatus.appoved, false),
                Arguments.of(OrderStatus.delivered, false),
                Arguments.of(OrderStatus.placed, false)
        );
    }
}
