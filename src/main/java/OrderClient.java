import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient{

    private static final String ORDER_CREATE_URL ="/api/v1/orders";

    @Step("Создаем заказ")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_CREATE_URL)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse get() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_CREATE_URL)
                .then();
    }
}