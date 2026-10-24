package za.co.richfield.smartpantry;
import android.app.*; import android.content.*; import android.graphics.Color; import android.graphics.Typeface;
import android.view.*; import android.widget.*;
public class Ui {
    static final int GREEN=Color.rgb(32,105,76), INK=Color.rgb(32,45,39), BG=Color.rgb(246,248,246);
    static LinearLayout page(Activity a,String title) {
        LinearLayout root=new LinearLayout(a); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(20,18,20,16); root.setBackgroundColor(BG);
        TextView heading=new TextView(a); heading.setText(title); heading.setTextSize(25); heading.setTypeface(null,Typeface.BOLD); heading.setTextColor(GREEN); heading.setPadding(0,0,0,14); root.addView(heading);
        return root;
    }
    static TextView text(Context c,String s,int size) { TextView t=new TextView(c); t.setText(s); t.setTextSize(size); t.setTextColor(INK); t.setPadding(4,8,4,8); return t; }
    static Button button(Context c,String s) { Button b=new Button(c); b.setText(s); b.setAllCaps(false); return b; }
    static void nav(Activity a,LinearLayout root) {
        LinearLayout bar=new LinearLayout(a); bar.setOrientation(LinearLayout.HORIZONTAL);
        String[] labels={"Pantry","Recipes","Settings"}; Class<?>[] cls={PantryActivity.class,SuggestedRecipesActivity.class,SettingsActivity.class};
        for(int i=0;i<labels.length;i++){ final Class<?> target=cls[i]; Button b=button(a,labels[i]); b.setTextSize(12); bar.addView(b,new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1)); b.setOnClickListener(v->{ if(!a.getClass().equals(target)){ a.startActivity(new Intent(a,target)); a.finish(); }}); }
        root.addView(bar);
    }
}
