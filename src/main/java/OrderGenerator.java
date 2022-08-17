public class OrderGenerator {

    public static Order getDefault() {
        String firstName = "Max";
        String lastName = "Bikov";
        String address = "Rostov, 1";
        String metroStation = "12";
        String phone = "+78005553535";
        int rentTime = 9;
        String deliveryDate = "2023-06-06";
        String comment = "Bistro";
        String[] color = {"BLACK"};
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    public static Order getWithoutColor(String[] color) {
        String firstName = "Maksim";
        String lastName = "Melchakov";
        String address = "Rostov, 72";
        String metroStation = "5";
        String phone = "+78005553535";
        int rentTime = 10;
        String deliveryDate = "2023-06-06";
        String comment = "Не нужно мне привозить";
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}