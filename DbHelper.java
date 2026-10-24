package za.co.richfield.smartpantry;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import java.util.*;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;
    public DbHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry TEXT DEFAULT '')");
        db.execSQL("CREATE TABLE recipes(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, steps TEXT NOT NULL)");
        db.execSQL("CREATE TABLE recipe_ingredients(id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER NOT NULL, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, FOREIGN KEY(recipe_id) REFERENCES recipes(id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE settings(key TEXT PRIMARY KEY, value TEXT NOT NULL)");
        seedRecipes(db);
        db.execSQL("INSERT INTO settings(key,value) VALUES('expiry_alerts','true')");
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void seedRecipes(SQLiteDatabase db) {
        String[][] data = {
            {"Tomato Egg Scramble","tomato:2:piece;egg:2:piece;oil:1:tbsp","1. Chop the tomatoes. 2. Heat oil in a pan. 3. Add tomatoes and cook until soft. 4. Add beaten eggs and stir until cooked."},
            {"Garlic Tomato Pasta","pasta:200:g;tomato:2:piece;garlic:2:clove;oil:1:tbsp","1. Boil pasta. 2. Sauté chopped garlic in oil. 3. Add chopped tomatoes and simmer. 4. Toss sauce with drained pasta."},
            {"Cheese Omelette","egg:2:piece;cheese:30:g;milk:1:tbsp","1. Beat eggs with milk. 2. Pour into a lightly heated pan. 3. Add cheese and fold when the eggs are set."},
            {"Potato Hash","potato:2:piece;onion:1:piece;oil:1:tbsp;salt:1:pinch","1. Dice potatoes and onion. 2. Heat oil. 3. Fry potatoes until tender, add onion and salt, and cook until golden."},
            {"Banana Pancakes","banana:1:piece;egg:1:piece;flour:50:g;milk:60:ml","1. Mash banana. 2. Mix with egg, flour and milk. 3. Spoon into a warm pan and cook both sides."},
            {"Simple Fried Rice","rice:200:g;egg:1:piece;carrot:1:piece;soy sauce:1:tbsp;oil:1:tbsp","1. Cook rice and cool it. 2. Dice carrot. 3. Scramble egg in oil, add carrot and rice, then stir in soy sauce."},
            {"Peanut Banana Toast","bread:2:slice;peanut butter:2:tbsp;banana:1:piece","1. Toast bread. 2. Spread peanut butter. 3. Slice banana over the toast and serve."},
            {"Tuna Pasta","pasta:200:g;tuna:1:can;mayonnaise:2:tbsp;lemon:1:piece","1. Boil pasta and drain. 2. Drain tuna. 3. Mix pasta, tuna, mayonnaise and a squeeze of lemon."},
            {"Cheesy Potato Bake","potato:3:piece;cheese:60:g;milk:100:ml;salt:1:pinch","1. Slice potatoes thinly. 2. Layer in an oven dish with milk and salt. 3. Cover with cheese and bake until tender and golden."},
            {"Carrot Soup","carrot:3:piece;onion:1:piece;stock:500:ml;oil:1:tbsp","1. Chop carrot and onion. 2. Sauté onion in oil. 3. Add carrot and stock, simmer until soft, then blend carefully."},
            {"Apple Oat Porridge","oats:50:g;milk:200:ml;apple:1:piece;cinnamon:1:tsp","1. Dice apple. 2. Simmer oats and milk. 3. Stir in apple and cinnamon and cook until creamy."},
            {"Bean Tomato Stew","beans:1:can;tomato:2:piece;onion:1:piece;garlic:1:clove","1. Chop onion, tomato and garlic. 2. Cook onion and garlic until fragrant. 3. Add tomato and beans, simmer for 15 minutes."},
            {"Chicken Rice Bowl","chicken:200:g;rice:150:g;carrot:1:piece;soy sauce:1:tbsp","1. Cook rice. 2. Cut chicken into small pieces and cook through. 3. Add carrot and soy sauce; serve over rice."},
            {"Spinach Cheese Toast","bread:2:slice;spinach:50:g;cheese:40:g;butter:1:tbsp","1. Sauté spinach in butter. 2. Place on bread, top with cheese and toast until melted."},
            {"Lemon Garlic Chicken","chicken:200:g;lemon:1:piece;garlic:2:clove;oil:1:tbsp;salt:1:pinch","1. Mix lemon juice, minced garlic, oil and salt. 2. Coat chicken. 3. Cook thoroughly in a pan or oven."},
            {"Cucumber Tomato Salad","cucumber:1:piece;tomato:2:piece;lemon:1:piece;salt:1:pinch","1. Chop cucumber and tomato. 2. Add lemon juice and salt. 3. Toss and serve."},
            {"Garlic Butter Mushrooms","mushroom:200:g;garlic:2:clove;butter:1:tbsp;salt:1:pinch","1. Slice mushrooms and mince garlic. 2. Melt butter in a pan. 3. Cook mushrooms and garlic until tender; season with salt."},
            {"Quick Cheese Quesadilla","tortilla:2:piece;cheese:60:g;beans:0.5:can","1. Spread beans over a tortilla. 2. Add cheese and top with the second tortilla. 3. Toast in a pan on both sides."}
        };
        for (String[] r : data) {
            ContentValues cv = new ContentValues(); cv.put("name",r[0]); cv.put("steps",r[2]);
            long rid = db.insert("recipes",null,cv);
            for (String part : r[1].split(";")) {
                String[] p=part.split(":");
                ContentValues iv=new ContentValues(); iv.put("recipe_id",rid); iv.put("name",p[0]);
                iv.put("quantity",Double.parseDouble(p[1])); iv.put("unit",p[2]); db.insert("recipe_ingredients",null,iv);
            }
        }
    }

    public long addIngredient(String name,double quantity,String unit,String expiry) {
        ContentValues v=new ContentValues(); v.put("name",canonical(name)); v.put("quantity",quantity); v.put("unit",unit.trim().toLowerCase(Locale.ROOT)); v.put("expiry",expiry);
        return getWritableDatabase().insert("pantry",null,v);
    }
    public int updateIngredient(long id,String name,double quantity,String unit,String expiry) {
        ContentValues v=new ContentValues(); v.put("name",canonical(name)); v.put("quantity",quantity); v.put("unit",unit.trim().toLowerCase(Locale.ROOT)); v.put("expiry",expiry);
        return getWritableDatabase().update("pantry",v,"id=?",new String[]{String.valueOf(id)});
    }
    public int deleteIngredient(long id) { return getWritableDatabase().delete("pantry","id=?",new String[]{String.valueOf(id)}); }
    public List<Ingredient> getPantry() {
        List<Ingredient> list=new ArrayList<>();
        try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,COALESCE(expiry,'') FROM pantry ORDER BY name",null)) {
            while(c.moveToNext()) list.add(new Ingredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4)));
        } return list;
    }
    public Ingredient getIngredient(long id) {
        try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,COALESCE(expiry,'') FROM pantry WHERE id=?",new String[]{String.valueOf(id)})) {
            if(c.moveToFirst()) return new Ingredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4));
        } return null;
    }
    public List<Recipe> getRecipes() {
        List<Recipe> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        try(Cursor c=db.rawQuery("SELECT id,name,steps FROM recipes ORDER BY name",null)) {
            while(c.moveToNext()) {
                Recipe r=new Recipe(c.getLong(0),c.getString(1),c.getString(2));
                try(Cursor ic=db.rawQuery("SELECT name,quantity,unit FROM recipe_ingredients WHERE recipe_id=?",new String[]{String.valueOf(r.id)})) {
                    while(ic.moveToNext()) r.ingredients.add(new Recipe.RequiredIngredient(ic.getString(0),ic.getDouble(1),ic.getString(2)));
                } list.add(r);
            }
        } return list;
    }
    public Recipe getRecipe(long id) { for(Recipe r:getRecipes()) if(r.id==id) return r; return null; }

    // Strict match: every required ingredient must exist in sufficient quantity and compatible unit.
    public List<Recipe> getSuggestedRecipes() {
        List<Recipe> result=new ArrayList<>(); List<Ingredient> pantry=getPantry();
        for(Recipe r:getRecipes()) {
            boolean match=true;
            for(Recipe.RequiredIngredient req:r.ingredients) {
                Ingredient found=null;
                for(Ingredient have:pantry) if(canonical(have.name).equals(canonical(req.name))) { found=have; break; }
                if(found==null || !unitsCompatible(found.unit,req.unit) || found.quantity < req.quantity) { match=false; break; }
            }
            if(match) result.add(r);
        } return result;
    }
    public boolean getExpiryAlerts() {
        try(Cursor c=getReadableDatabase().rawQuery("SELECT value FROM settings WHERE key='expiry_alerts'",null)) {
            return c.moveToFirst() && Boolean.parseBoolean(c.getString(0));
        }
    }
    public void setExpiryAlerts(boolean enabled) {
        ContentValues v=new ContentValues(); v.put("key","expiry_alerts"); v.put("value",String.valueOf(enabled));
        getWritableDatabase().insertWithOnConflict("settings",null,v,SQLiteDatabase.CONFLICT_REPLACE);
    }
    public static String canonical(String s) {
        String n=s==null?"":s.trim().toLowerCase(Locale.ROOT);
        if(n.endsWith("ies") && n.length()>4) n=n.substring(0,n.length()-3)+"y";
        else if(n.endsWith("oes") && n.length()>4) n=n.substring(0,n.length()-2);
        else if(n.endsWith("s") && !n.endsWith("ss") && n.length()>3) n=n.substring(0,n.length()-1);
        return n;
    }
    private boolean unitsCompatible(String a,String b) {
        a=a.toLowerCase(Locale.ROOT).trim(); b=b.toLowerCase(Locale.ROOT).trim();
        if(a.equals(b)) return true;
        // Conservative conversions for common mass/volume units.
        if(a.equals("kg") && b.equals("g")) return true;
        if(a.equals("g") && b.equals("kg")) return true;
        if(a.equals("l") && b.equals("ml")) return true;
        if(a.equals("ml") && b.equals("l")) return true;
        return false;
    }
}
