import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class DeleteCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("���������, ��� ������� ����� ������� (� ��������� �������)")
    @Description("����� /api/v1/courier/:id")
    public void courierCanBeDeletedTest () {
        courier = CourierGenerator.getDefault();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse deleteResponse = courierClient.delete(courierId);

        int deleteStatusCode = deleteResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, deleteStatusCode); // ��������� ������ ��� ������ 200

        boolean bodyAnswer = deleteResponse.extract().path("ok");
        assertTrue("Body message is incorrect", bodyAnswer); // ��������� ��������� � ���� ������
    }

    @Test
    @DisplayName("���������, ��� ���� ������� �� ������������ id �������� ������")
    @Description("����� /api/v1/courier/:id")
    public void courierCantBeDeletedWithNonExistentIdTest () {
        ValidatableResponse response = courierClient.delete(999_999_999);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_NOT_FOUND, statusCode); // ��������� ������ ��� ������ 404

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "������� � ����� id ���.", bodyAnswer); // ��������� ��������� � ���� ������
    }

    @Test
    @DisplayName("���������, ��� ���� �� ������� id, �� �������� ������")
    @Description("����� /api/v1/courier")
    public void courierCantBeDeletedWithoutIdTest () {
        ValidatableResponse response = courierClient.deleteWithoutId();

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_NOT_FOUND, statusCode); // ��������� ������ ��� ������ 404

        String bodyAnswer = response.extract().path("message");
        assertEquals("The body message is incorrect", "Not Found.", bodyAnswer); // ��������� ��������� � ���� ������
    }
}
