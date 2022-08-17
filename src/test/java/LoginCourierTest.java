import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LoginCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private  int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Проверяем, что курьер может авторизоваться(залогиниться) с валидными данными")
    @Description("ручка /api/v1/courier/login")
    public void courierCanLoginTest() {
        courierClient.create(courier); // создаем курьера
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_OK, statusCodeLogin); // Проверка статус кода 200

        courierId = responseLogin.extract().path("id");
        assertNotEquals("Courier id is incorrect (=0)",0 ,courierId); // Проверка, что id не равен 0
    }

    @Test
    @DisplayName("Проверяем, что курьер не может авторизоваться с пустым логином")
    @Description("ручка /api/v1/courier/login")
    public void courierCantLoginWithEmptyLoginTest() {
        courierClient.create(courier); // создаем курьера
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials("", courier.getPassword()));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCodeLogin); // Проверка статус кода 400

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "Недостаточно данных для входа", bodyAnswer); // Проверка сообщения в теле ответа

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // Логинимся с корректными данными и ложим id в переменную для удаления созданного курьера
    }

    @Test
    @DisplayName("Проверяем, что курьер не может авторизоваться с пустым паролем")
    @Description("ручка /api/v1/courier/login")
    public void courierCantLoginWithEmptyPasswordTest() {
        courierClient.create(courier); // создаем курьера
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(courier.getLogin(), ""));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCodeLogin); // Проверка статус кода 400

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "Недостаточно данных для входа", bodyAnswer); // Проверка сообщения в теле ответа

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // Логинимся с корректными данными и ложим id в переменную для удаления созданного курьера
    }

    @Test
    @DisplayName("Проверяем, что курьер не может авторизоваться под логином со значением null")
    @Description("ручка /api/v1/courier/login")
    public void courierCantLoginWithNullLoginValueTest() {
        courierClient.create(courier); // создаем курьера
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(null, courier.getPassword()));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCodeLogin); // Проверка статус кода 400

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "Недостаточно данных для входа", bodyAnswer); // Проверка сообщения в теле ответа

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // Логинимся с корректными данными и ложим id в переменную для удаления созданного курьера
    }

    @Test
    @DisplayName("Проверяем, что курьер не может авторизоваться под паролем со значением null")
    @Description("ручка /api/v1/courier/login")
    public void courierCantLoginWithNullPasswordValueTest() {
        courierClient.create(courier); // создаем курьера
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(courier.getLogin(), null));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCodeLogin); // Проверка статус кода 400

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "Недостаточно данных для входа", bodyAnswer); // Проверка сообщения в теле ответа

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // Логинимся с корректными данными и ложим id в переменную для удаления созданного курьера
    }

    @Test
    @DisplayName("Проверяем, что курьер не может авторизоваться с неправильным логином")
    @Description("ручка /api/v1/courier/login")
    public void courierCantLoginWithIncorrectLoginValueTest() {
        courierClient.create(courier); // создаем курьера
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials("Dora", courier.getPassword()));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCodeLogin); // Проверка статус кода 404

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "Учетная запись не найдена", bodyAnswer); // Проверка сообщения в теле ответа

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // Логинимся с корректными данными и ложим id в переменную для удаления созданного курьера
    }

    @Test
    @DisplayName("Проверяем, что курьер не может авторизоваться с неправильным паролем")
    @Description("ручка /api/v1/courier/login")
    public void courierCantLoginWithIncorrectPasswordValueTest() {
        courierClient.create(courier); // создаем курьера
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(courier.getLogin(), "Dora"));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCodeLogin); // Проверка статус кода 404

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "Учетная запись не найдена", bodyAnswer); // Проверка сообщения в теле ответа

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // Логинимся с корректными данными и ложим id в переменную для удаления созданного курьера
    }

    @Test
    @DisplayName("Проверяем, что нельзя авторизоваться под несуществующим пользователем")
    @Description("ручка /api/v1/courier/login")
    public void cantLoginWithNonExistentCourierTest() {
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials("Dora", "Dura"));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCodeLogin); // Проверка статус кода 404

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "Учетная запись не найдена", bodyAnswer); // Проверка сообщения в теле ответа
    }
}