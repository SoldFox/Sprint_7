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
    @DisplayName("���������, ��� ������� ����� ������� (� ��������� �������)")
    @Description("����� /api/v1/courier")
    public void courierCanBeCreatedTest () {
        courier = CourierGenerator.getDefault();
        ValidatableResponse response = courierClient.create(courier); // ������� �������

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode); // ��������� ������ ��� ������ �������� ������� 201

        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created",isCreated); // ��������� ��������� � ���� ������ �������� ������� - true ��� false

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, loginStatusCode); // ��������� ������ ��� ������ 200

        courierId = loginResponse.extract().path("id");
        assertNotEquals("Courier id is incorrect (=0)",0, courierId); // ��������� ��������� � ���� ������ ������
    }

    @Test
    @DisplayName("���������, ��� ������ ������� 2�� ���������� �������")
    @Description("����� /api/v1/courier")
    public void cantCreateTwoIdenticalCouriersTest () {
        courier = CourierGenerator.getDefault();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        ValidatableResponse secondResponse = courierClient.create(courier);
        int secondStatusCode = secondResponse.extract().statusCode();
        assertEquals("Status code is not a 409", SC_CONFLICT, secondStatusCode); // ��������� ������ ��� 409

        String bodyAnswer = secondResponse.extract().path("message");
        assertEquals("The body message is incorrect","���� ����� ��� ������������. ���������� ������.", bodyAnswer); // ��������� ��������� � ���� ������
    }

    @Test
    @DisplayName("���������, ��� ������ ������� ������� �� ��������� ������ - null")
    @Description("����� /api/v1/courier")
    public void cantCreateCourierWithNullLoginValueTest () {
        courier = CourierGenerator.getCourierWithNullLoginValue();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode); // ��������� ������ ��� 400

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "������������ ������ ��� �������� ������� ������", bodyAnswer); // ��������� ��������� � ���� ������
    }

    @Test
    @DisplayName("���������, ��� ������ ������� ������� �� ��������� ������ - null")
    @Description("����� /api/v1/courier")
    public void cantCreateCourierWithNullPasswordValueTest () {
        courier = CourierGenerator.getCourierWithNullPasswordValue();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode); // ��������� ������ ��� 400

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "������������ ������ ��� �������� ������� ������", bodyAnswer); // ��������� ��������� � ���� ������
    }

    @Test
    @DisplayName("���������, ��� ����� ������� ������� �� ��������� ���� ����� - null")
    @Description("����� /api/v1/courier")
    public void courierCanBeCreatedWithNullFirstNameValueTest () {
        courier = CourierGenerator.getCourierWithNullFirstNameValue();
        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode); // ��������� ������ ��� 201

        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created",isCreated); // ��������� ��������� � ���� ������ �������� ������� - true ��� false
    }

    @Test
    @DisplayName("���������, ��� ������ ������� ������� � ������ ��������� ������")
    @Description("����� /api/v1/courier")
    public void cantCreateCourierWithEmptyLoginTest () {
        courier = CourierGenerator.getCourierWithEmptyLogin();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode); // ��������� ������ ��� 400

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "������������ ������ ��� �������� ������� ������", bodyAnswer); // ��������� ��������� � ���� ������
    }

    @Test
    @DisplayName("���������, ��� ������ ������� ������� � ������ ��������� ������")
    @Description("����� /api/v1/courier")
    public void cantCreateCourierWithEmptyPasswordTest () {
        courier = CourierGenerator.getCourierWithEmptyPassword();
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode); // ��������� ������ ��� 400

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "������������ ������ ��� �������� ������� ������", bodyAnswer); // ��������� ��������� � ���� ������
    }
}