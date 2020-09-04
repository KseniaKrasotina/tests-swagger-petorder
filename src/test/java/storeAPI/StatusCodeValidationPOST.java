package storeAPI;

import Utils.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StatusCodeValidationPOST {

    @Test
    @DisplayName("1.1. Check status. Check Correct header. Correct HTTP type. POST request. Correct Payload")
    public void testCorrectPayload()
    {
        OrderDTO order = new OrderDTO(0, 0, 0, "2020-09-02T09:47:32.334Z", OrderStatus.placed, true);

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(order.toJson())
            .when()
            .post(Config.BASE_URL+"store/order")
            .then()
            .statusCode(200)
            .header("access-control-allow-origin","*");
    }

    @Test
    @DisplayName("1.2. Check status code. Correct HTTP type. POST request. Incorrect URL")
    public void testIncorrectURL()
    {
        String requestBody = "";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(Config.BASE_URL)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("1.3. Checking status code. Correct HTTP type. POST request. No Body")
    public void testEmptyBody()
    {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .post(Config.BASE_URL+"store/order")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("1.4. Check status. Incorrect HTTP type.  Correct Payload")
    public void testIncorrectMethod()
    {
        OrderDTO order = new OrderDTO(0, 0, 0, "2020-09-02T09:47:32.334Z", OrderStatus.placed, true);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(order.toJson())
                .when()
                .get(Config.BASE_URL+"store/order")
                .then()
                .statusCode(405);
    }

    @Test
    @DisplayName("1.5. Check status. Correct HTTP type. POST request. Correct Payload. Incorrect Content-Type")
    public void testIncorrectContentType()
    {
        OrderDTO order = new OrderDTO(0, 0, 0, "2020-09-02T09:47:32.334Z", OrderStatus.placed, true);

        RestAssured.given()
                .contentType(ContentType.XML)
                .body(order.toJson())
                .when()
                .post(Config.BASE_URL+"store/order")
                .then()
                .statusCode(415);
    }
    @Test
    @DisplayName("1.7. Check status. Correct HTTP type. POST request. Incorrect Payload (Incorrect Order)")
    public void testIncorrectPayload()
    {
        //Negative IDs
        OrderDTO order = new OrderDTO(-1, -1, 0, "2020-09-02T09:47:32.334Z", OrderStatus.placed, true);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(order.toJson())
                .when()
                .post(Config.BASE_URL+"store/order")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("1.8. Check status. Correct HTTP type. POST request. Incorrect odred data - invalid order")
    public void testIncorrectPDataFormat()
    {
        //incorrect petId type
        IncorrectOrderDTO incorrectorder  = new IncorrectOrderDTO(-1, "AAA", 0, 1, OrderStatus.placed, true);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(incorrectorder.toJson())
                .when()
                .post(Config.BASE_URL+"store/order")
                .then()
                .statusCode(500);
    }

}
