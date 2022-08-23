public class CourierGenerator {

    public static Courier getDefault() {
        return new Courier("Kaka","Koko", "Kako");
    }

    public static Courier getCourierWithNullLoginValue() {
        return new Courier(null, "Koko", "Kako");
    }

    public static Courier getCourierWithNullPasswordValue() {
        return new Courier("Kaka", null, "Kako");
    }

    public static Courier getCourierWithEmptyLogin() {
        return new Courier("", "Koko", "Kako");
    }

    public static Courier getCourierWithEmptyPassword() {
        return new Courier("Kaka", "", "Kako");
    }

    public static Courier getCourierWithNullFirstNameValue() {
        return new Courier("Kaka", "Koko", null);
    }
}