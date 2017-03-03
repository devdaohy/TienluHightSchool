package comkakashipham05.httpsfacebook.thpttienlu.SplashScreen;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

import comkakashipham05.httpsfacebook.thpttienlu.Alarm;
import comkakashipham05.httpsfacebook.thpttienlu.MainActivity;
import comkakashipham05.httpsfacebook.thpttienlu.R;
import comkakashipham05.httpsfacebook.thpttienlu.Service.ServiceClass;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences share = getSharedPreferences("MyShare", MODE_PRIVATE);
        final SharedPreferences.Editor editor = share.edit();
        startService(new Intent(SplashScreen.this, ServiceClass.class));
        if ("TRUE".equals(share.getString("KiemtraService","")))
        {
        }
        else {
            editor.putString("switchUpdateTKB","ON");
            editor.putString("KiemtraService","TRUE");
            editor.commit();
        }
        final Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2500);
                } catch(InterruptedException e) {
                } finally {
                    finish();
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        };
        splashTread.start();
    }
    private void handleNotification() {
        Calendar calendar = Calendar.getInstance();
        Intent alarmIntent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 6000, pendingIntent);
    }
}
