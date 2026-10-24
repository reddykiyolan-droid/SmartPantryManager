package za.co.richfield.smartpantry;
import android.app.*;import android.content.*;import android.os.Bundle;import android.widget.*;import java.util.*;
public class AllRecipesActivity extends Activity {
 DbHelper db; List<Recipe> recipes;
 @Override public void onCreate(Bundle b){super.onCreate(b);db=new DbHelper(this);LinearLayout root=Ui.page(this,"Recipe Collection");root.addView(Ui.text(this,"Browse the seeded collection. Items here are not necessarily strict matches.",14));
 recipes=db.getRecipes();ListView list=new ListView(this);ArrayAdapter<String>a=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);for(Recipe r:recipes)a.add(r.name);list.setAdapter(a);root.addView(list,new LinearLayout.LayoutParams(-1,0,1));
 list.setOnItemClickListener((p,v,pos,id)->{Intent i=new Intent(this,RecipeDetailActivity.class);i.putExtra("recipe_id",recipes.get(pos).id);startActivity(i);});Ui.nav(this,root);setContentView(root);}
}
