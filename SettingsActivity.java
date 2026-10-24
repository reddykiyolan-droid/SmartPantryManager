package za.co.richfield.smartpantry;
import android.app.*;import android.os.Bundle;import android.widget.*;
public class SettingsActivity extends Activity {
 @Override public void onCreate(Bundle b){super.onCreate(b);DbHelper db=new DbHelper(this);LinearLayout root=Ui.page(this,"Settings");
 root.addView(Ui.text(this,"Preferences",18));Switch alerts=new Switch(this);alerts.setText("Expiry reminder preference");alerts.setTextSize(16);alerts.setChecked(db.getExpiryAlerts());root.addView(alerts);alerts.setOnCheckedChangeListener((v,on)->{db.setExpiryAlerts(on);Toast.makeText(this,"Preference saved.",Toast.LENGTH_SHORT).show();});
 root.addView(Ui.text(this,"This setting stores your preference locally. Automatic notifications are not implemented in this version.",13));
 root.addView(Ui.text(this,"Storage: SQLite database on this device. Pantry items and preferences persist after closing the app.",14));Ui.nav(this,root);setContentView(root);}
}
