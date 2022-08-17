import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    private static final String COURIER_CREATE_URL = "/api/v1/courier/";

    @Step("������� �������")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_CREATE_URL)
                .then();
    }

    @Step("��������� �������� �� login � password")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_CREATE_URL + "login")
                .then();
    }

    @Step("������� ������� �� id")
    public ValidatableResponse  delete (int id) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_CREATE_URL + id)
                .then();
    }

    @Step("������� ������� ��� id")
    public ValidatableResponse  deleteWithoutId () {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_CREATE_URL)
                .then();
    }
}