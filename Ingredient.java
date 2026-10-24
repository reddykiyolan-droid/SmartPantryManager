package za.co.richfield.smartpantry;
public class Ingredient {
    public long id; public String name, unit, expiry; public double quantity;
    public Ingredient(long id, String name, double quantity, String unit, String expiry) {
        this.id=id; this.name=name; this.quantity=quantity; this.unit=unit; this.expiry=expiry;
    }
    @Override public String toString() { return name + " — " + quantity + " " + unit + (expiry.isEmpty() ? "" : " · expires " + expiry); }
}
