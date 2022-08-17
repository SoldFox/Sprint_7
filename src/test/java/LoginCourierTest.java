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
    @DisplayName("���������, ��� ������ ����� ��������������(������������) � ��������� �������")
    @Description("����� /api/v1/courier/login")
    public void courierCanLoginTest() {
        courierClient.create(courier); // ������� �������
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_OK, statusCodeLogin); // �������� ������ ���� 200

        courierId = responseLogin.extract().path("id");
        assertNotEquals("Courier id is incorrect (=0)",0 ,courierId); // ��������, ��� id �� ����� 0
    }

    @Test
    @DisplayName("���������, ��� ������ �� ����� �������������� � ������ �������")
    @Description("����� /api/v1/courier/login")
    public void courierCantLoginWithEmptyLoginTest() {
        courierClient.create(courier); // ������� �������
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials("", courier.getPassword()));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCodeLogin); // �������� ������ ���� 400

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "������������ ������ ��� �����", bodyAnswer); // �������� ��������� � ���� ������

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // ��������� � ����������� ������� � ����� id � ���������� ��� �������� ���������� �������
    }

    @Test
    @DisplayName("���������, ��� ������ �� ����� �������������� � ������ �������")
    @Description("����� /api/v1/courier/login")
    public void courierCantLoginWithEmptyPasswordTest() {
        courierClient.create(courier); // ������� �������
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(courier.getLogin(), ""));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCodeLogin); // �������� ������ ���� 400

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "������������ ������ ��� �����", bodyAnswer); // �������� ��������� � ���� ������

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // ��������� � ����������� ������� � ����� id � ���������� ��� �������� ���������� �������
    }

    @Test
    @DisplayName("���������, ��� ������ �� ����� �������������� ��� ������� �� ��������� null")
    @Description("����� /api/v1/courier/login")
    public void courierCantLoginWithNullLoginValueTest() {
        courierClient.create(courier); // ������� �������
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(null, courier.getPassword()));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCodeLogin); // �������� ������ ���� 400

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "������������ ������ ��� �����", bodyAnswer); // �������� ��������� � ���� ������

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // ��������� � ����������� ������� � ����� id � ���������� ��� �������� ���������� �������
    }

    @Test
    @DisplayName("���������, ��� ������ �� ����� �������������� ��� ������� �� ��������� null")
    @Description("����� /api/v1/courier/login")
    public void courierCantLoginWithNullPasswordValueTest() {
        courierClient.create(courier); // ������� �������
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(courier.getLogin(), null));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCodeLogin); // �������� ������ ���� 400

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "������������ ������ ��� �����", bodyAnswer); // �������� ��������� � ���� ������

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // ��������� � ����������� ������� � ����� id � ���������� ��� �������� ���������� �������
    }

    @Test
    @DisplayName("���������, ��� ������ �� ����� �������������� � ������������ �������")
    @Description("����� /api/v1/courier/login")
    public void courierCantLoginWithIncorrectLoginValueTest() {
        courierClient.create(courier); // ������� �������
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials("Dora", courier.getPassword()));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCodeLogin); // �������� ������ ���� 404

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "������� ������ �� �������", bodyAnswer); // �������� ��������� � ���� ������

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // ��������� � ����������� ������� � ����� id � ���������� ��� �������� ���������� �������
    }

    @Test
    @DisplayName("���������, ��� ������ �� ����� �������������� � ������������ �������")
    @Description("����� /api/v1/courier/login")
    public void courierCantLoginWithIncorrectPasswordValueTest() {
        courierClient.create(courier); // ������� �������
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials(courier.getLogin(), "Dora"));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCodeLogin); // �������� ������ ���� 404

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "������� ������ �� �������", bodyAnswer); // �������� ��������� � ���� ������

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id"); // ��������� � ����������� ������� � ����� id � ���������� ��� �������� ���������� �������
    }

    @Test
    @DisplayName("���������, ��� ������ �������������� ��� �������������� �������������")
    @Description("����� /api/v1/courier/login")
    public void cantLoginWithNonExistentCourierTest() {
        ValidatableResponse responseLogin = courierClient.login(new CourierCredentials("Dora", "Dura"));

        int statusCodeLogin = responseLogin.extract().statusCode();
        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCodeLogin); // �������� ������ ���� 404

        String bodyAnswer = responseLogin.extract().path("message");
        assertEquals("Text in body is incorrect", "������� ������ �� �������", bodyAnswer); // �������� ��������� � ���� ������
    }
}