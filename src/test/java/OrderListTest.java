import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class OrderListTest {

    private  OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("��������� ��� ��� ��������� ������ ������� �� �� ������")
    @Description("����� /api/v1/orders")
    public void getOrderListIsNotEmptyTest () {
        ValidatableResponse response = orderClient.get();

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, statusCode); // ��������� ��� ������ ��� 200

        List<String> bodyAnswer = response.extract().path("orders");
        assertFalse("bodyAnswer is empty", bodyAnswer.isEmpty()); // ��������� ��� ���� ������ �� ������
    }

    @Test
    @DisplayName("��������� ��� ��� ��������� ������ ������� �� �� ����� null")
    @Description("����� /api/v1/orders")
    public void getOrderListIsNotNull () {
        ValidatableResponse response = orderClient.get();

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, statusCode); // ��������� ��� ������ ��� 200

        List<String> bodyAnswer = response.extract().path("orders");
        assertNotEquals("bodyAnswer is null", null, bodyAnswer); // ��������� ��� ���� ������ �� ����� null
    }
}