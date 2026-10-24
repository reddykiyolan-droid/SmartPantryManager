package za.co.richfield.smartpantry;
import android.app.*; import android.content.*; import android.os.Bundle; import android.view.*; import android.widget.*; import java.util.*;
public class PantryActivity extends Activity {
    DbHelper db; ArrayAdapter<Ingredient> adapter; List<Ingredient> items; ListView list;
    @Override public void onCreate(Bundle b){super.onCreate(b); db=new DbHelper(this); render();}
    @Override protected void onResume(){super.onResume(); if(db!=null) render();}
    void render(){
        LinearLayout root=Ui.page(this,"Smart Pantry Manager"); root.addView(Ui.text(this,"Track what you have. Cook with what is already at home.",15));
        Button add=Ui.button(this,"+ Add ingredient"); root.addView(add); add.setOnClickListener(v->startActivity(new Intent(this,IngredientFormActivity.class)));
        root.addView(Ui.text(this,"Your pantry (tap an item to edit; long-press to delete)",14));
        items=db.getPantry(); list=new ListView(this); adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items); list.setAdapter(adapter);
        root.addView(list,new LinearLayout.LayoutParams(-1,0,1));
        list.setOnItemClickListener((p,v,pos,id)->{Intent i=new Intent(this,IngredientFormActivity.class); i.putExtra("ingredient_id",items.get(pos).id); startActivity(i);});
        list.setOnItemLongClickListener((p,v,pos,id)->{Ingredient item=items.get(pos); new AlertDialog.Builder(this).setTitle("Delete ingredient?").setMessage("Remove "+item.name+" from your pantry?").setNegativeButton("Cancel",null).setPositiveButton("Delete",(d,w)->{db.deleteIngredient(item.id);render();}).show(); return true;});
        if(items.isEmpty()) root.addView(Ui.text(this,"Your pantry is empty. Add an ingredient to get started.",14));
        Ui.nav(this,root); setContentView(root);
    }
}
