package comkakashipham05.httpsfacebook.thpttienlu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import comkakashipham05.httpsfacebook.thpttienlu.Service.ServiceClass;
import comkakashipham05.httpsfacebook.thpttienlu.SplashScreen.SplashScreen;

public class setting extends AppCompatActivity {
    Switch switchUpdateTKB;
    Button saveSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SharedPreferences share = getSharedPreferences("MyShare", MODE_PRIVATE);
        final SharedPreferences.Editor editor = share.edit();
        switchUpdateTKB = (Switch) findViewById(R.id.switchUpdateTKB);
        saveSetting = (Button) findViewById(R.id.saveSetting);
        final Intent intent = new Intent(setting.this,ServiceClass.class);
        if ("ON".equals(share.getString("switchUpdateTKB",""))) {
            switchUpdateTKB.setChecked(true);
        }
        else switchUpdateTKB.setChecked(false);
        saveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchUpdateTKB.isChecked()){
                    editor.putString("switchUpdateTKB","ON");
                }
                else{
                    editor.putString("switchUpdateTKB","OFF");
                }
                editor.commit();
                MainActivity.getInstance().finish();
                Intent backMain = new Intent(setting.this,MainActivity.class);
                startActivity(backMain);
                finish();
            }
        });

    }
}
