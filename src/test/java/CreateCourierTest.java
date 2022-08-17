import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("ѕровер€ем, что курьера можно создать (с валидными данными)")
    @Description("ручка /api/v1/courier")
    public void courierCanBeCreatedTest () {
        courier = CourierGenerator.getDefault();
        ValidatableResponse response = courierClient.create(courier); // —оздаем курьера

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode); // ѕровер€ем статус код ответа создани€ курьера 201

        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created",isCreated); // ѕровер€ем сообщение в теле ответа создани€ курьера - true или false

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, loginStatusCode); // ѕровер€ем статус код логина 200

        courierId = loginResponse.extract().path("id");
        assertNotEquals("Courier id is incorrect (=0)",0, courierId); // ѕровер€ем сообщение в теле ответа логина
    }

    @Test
    @DisplayName("ѕровер€ем, что нельз€ создать 2ух одинаковых курьера")
    @Description("ручка /api/v1/courier")
    public void cantCreateTwoIdenticalCouriersTest () {
        courier = CourierGenerator.getDefault();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        ValidatableResponse secondResponse = courierClient.create(courier);
        int secondStatusCode = secondResponse.extract().statusCode();
        assertEquals("Status code is not a 409", SC_CONFLICT, secondStatusCode); // ѕровер€ем статус код 409

        String bodyAnswer = secondResponse.extract().path("message");
        assertEquals("The body message is incorrect","Ётот логин уже используетс€. ѕопробуйте другой.", bodyAnswer); // ѕровер€ем сообщение в теле ответа
    }

    @Test
    @DisplayName("ѕровер€ем, что нельз€ создать курьера со значениме логина - null")
    @Description("ручка /api/v1/courier")
    public void cantCreateCourierWithNullLoginValueTest () {
        courier = CourierGenerator.getCourierWithNullLoginValue();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode); // ѕровер€ем статус код 400

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "Ќедостаточно данных дл€ создани€ учетной записи", bodyAnswer); // ѕровер€ем сообщение в теле ответа
    }

    @Test
    @DisplayName("ѕровер€ем, что нельз€ создать курьера со значениме парол€ - null")
    @Description("ручка /api/v1/courier")
    public void cantCreateCourierWithNullPasswordValueTest () {
        courier = CourierGenerator.getCourierWithNullPasswordValue();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode); // ѕровер€ем статус код 400

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "Ќедостаточно данных дл€ создани€ учетной записи", bodyAnswer); // ѕровер€ем сообщение в теле ответа
    }

    @Test
    @DisplayName("ѕровер€ем, что можно создать курьера со значениме пол€ имени - null")
    @Description("ручка /api/v1/courier")
    public void courierCanBeCreatedWithNullFirstNameValueTest () {
        courier = CourierGenerator.getCourierWithNullFirstNameValue();
        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode); // ѕровер€ем статус код 201

        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created",isCreated); // ѕровер€ем сообщение в теле ответа создани€ курьера - true или false
    }

    @Test
    @DisplayName("ѕровер€ем, что нельз€ создать курьера с пустым значением логина")
    @Description("ручка /api/v1/courier")
    public void cantCreateCourierWithEmptyLoginTest () {
        courier = CourierGenerator.getCourierWithEmptyLogin();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode); // ѕровер€ем статус код 400

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "Ќедостаточно данных дл€ создани€ учетной записи", bodyAnswer); // ѕровер€ем сообщение в теле ответа
    }

    @Test
    @DisplayName("ѕровер€ем, что нельз€ создать курьера с пустым значением парол€")
    @Description("ручка /api/v1/courier")
    public void cantCreateCourierWithEmptyPasswordTest () {
        courier = CourierGenerator.getCourierWithEmptyPassword();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode); // ѕровер€ем статус код 400

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "Ќедостаточно данных дл€ создани€ учетной записи", bodyAnswer); // ѕровер€ем сообщение в теле ответа
    }
}