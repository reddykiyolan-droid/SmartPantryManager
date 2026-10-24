package za.co.richfield.smartpantry;
import android.app.*; import android.content.*; import android.os.Bundle; import android.view.*; import android.widget.*; import java.util.*;
public class SuggestedRecipesActivity extends Activity {
    DbHelper db; List<Recipe> recipes;
    @Override public void onCreate(Bundle b){super.onCreate(b);db=new DbHelper(this);render();}
    @Override protected void onResume(){super.onResume();if(db!=null)render();}
    void render(){
        LinearLayout root=Ui.page(this,"Suggested Recipes"); root.addView(Ui.text(this,"Only recipes with every required ingredient in sufficient quantity appear here.",14));
        recipes=db.getSuggestedRecipes(); ListView list=new ListView(this); ArrayAdapter<String> a=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        for(Recipe r:recipes)a.add(r.name+"  ·  "+r.ingredients.size()+" ingredients"); list.setAdapter(a);
        if(recipes.isEmpty())root.addView(Ui.text(this,"No recipes match your pantry yet — add more ingredients or check quantities and units.",15));
        root.addView(list,new LinearLayout.LayoutParams(-1,0,1)); list.setOnItemClickListener((p,v,pos,id)->{Intent i=new Intent(this,RecipeDetailActivity.class);i.putExtra("recipe_id",recipes.get(pos).id);startActivity(i);});
        Button all=Ui.button(this,"Browse all recipes");root.addView(all);all.setOnClickListener(v->startActivity(new Intent(this,AllRecipesActivity.class)));
        Ui.nav(this,root);setContentView(root);
    }
}
