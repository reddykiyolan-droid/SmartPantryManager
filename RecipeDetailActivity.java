package za.co.richfield.smartpantry;
import android.app.*;import android.os.Bundle;import android.widget.*;
public class RecipeDetailActivity extends Activity {
 @Override public void onCreate(Bundle b){super.onCreate(b);DbHelper db=new DbHelper(this);long id=getIntent().getLongExtra("recipe_id",-1);Recipe r=db.getRecipe(id);
 LinearLayout root=Ui.page(this,r==null?"Recipe not found":r.name);if(r!=null){root.addView(Ui.text(this,"Required ingredients",18));for(Recipe.RequiredIngredient x:r.ingredients)root.addView(Ui.text(this,"• "+x.name+" — "+x.quantity+" "+x.unit,15));root.addView(Ui.text(this,"Preparation method",18));root.addView(Ui.text(this,r.steps,15));}
 Button back=Ui.button(this,"Back");root.addView(back);back.setOnClickListener(v->finish());Ui.nav(this,root);setContentView(root);}
}
