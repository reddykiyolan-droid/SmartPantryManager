package za.co.richfield.smartpantry;
import java.util.*;
public class Recipe {
    public long id; public String name, steps; public List<RequiredIngredient> ingredients = new ArrayList<>();
    public Recipe(long id, String name, String steps) { this.id=id; this.name=name; this.steps=steps; }
    public static class RequiredIngredient {
        public String name, unit; public double quantity;
        public RequiredIngredient(String name, double quantity, String unit) { this.name=name; this.quantity=quantity; this.unit=unit; }
    }
}
