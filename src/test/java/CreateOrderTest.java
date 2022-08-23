import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private  Order order;
    private  OrderClient orderClient;
    private final String[] color;

    public CreateOrderTest (String color) {
        this.color = new String[] {color};
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters
    public static Object[][] setColor () {
        return new Object[][] {
                {"BLACK"},
                {"GREY"},
                {""},
                {"BLACK , GREY"}
        };
    }

    @Test
    @DisplayName("��������� �������� ������, � ������� - ������, �����, ������ ����, ������ � ����� �����")
    @Description("����� /api/v1/orders")
    public void orderCanBeCreatedTest() {
        order = OrderGenerator.getWithoutColor(color); // ������� ����� � �������� ��� ���� �� ��������������
        ValidatableResponse response = orderClient.create(order);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode); // ��������� ������ ��� 201

        int track = response.extract().path("track");
        assertNotEquals("The track value is incorrect (=0)", 0, track); // ��������� ��� ����� ����� �� ����� 0
    }
}