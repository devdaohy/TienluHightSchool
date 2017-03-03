package comkakashipham05.httpsfacebook.thpttienlu.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comkakashipham05.httpsfacebook.thpttienlu.CUSTOMLIST.customThongBao;
import comkakashipham05.httpsfacebook.thpttienlu.MainActivity;
import comkakashipham05.httpsfacebook.thpttienlu.Model.ThongBao;
import comkakashipham05.httpsfacebook.thpttienlu.R;
import comkakashipham05.httpsfacebook.thpttienlu.thongbao;

/**
 * Created by Administrator on 09/02/2017.
 */

public class ServiceClass extends Service {
    private DatabaseReference mDatabase;
    private boolean kiemtra,kiemtra1;
    private String begin,kiemtraNotifications;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        kiemtra=false;
        kiemtra1=false;
        begin="";
        final SharedPreferences share = getSharedPreferences("MyShare", MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("TKB Start").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tkbStart = dataSnapshot.getValue().toString();
                if (kiemtra==false) {
                    kiemtra=true;
                    begin = tkbStart;
                }
                else {
                    if (tkbStart.equals(begin) ){
                    }
                    else {
                        begin=tkbStart;
                        if  ("ON".equals(share.getString("switchUpdateTKB",""))) {
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            Notification.Builder builder = new Notification.Builder(getApplicationContext());
                            builder.setContentTitle("Thay đổi thời khóa biểu");
                            builder.setSmallIcon(R.drawable.notification);
                            builder.setStyle(new Notification.BigTextStyle().bigText("Thời khóa biểu đã được thay đổi từ ngày: " + tkbStart + ", vào xem ngay nào !"));
                            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                            PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, 0);
                            builder.setContentIntent(resultPendingIntent);
                            builder.setAutoCancel(true);
                            notificationManager.notify(1, builder.build());
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("Kiem tra notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kiemtra=dataSnapshot.getValue().toString();
                if (kiemtra1==false){
                    kiemtra1=true;
                    kiemtraNotifications=kiemtra;
                }
                else {
                    if (kiemtra.equals(kiemtraNotifications)){

                    }
                    else {
                        Toast.makeText(ServiceClass.this, kiemtraNotifications, Toast.LENGTH_SHORT).show();
                        kiemtraNotifications=kiemtra;
                        mDatabase.child("Thông Báo").addValueEventListener(new ValueEventListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot data : dataSnapshot.getChildren()){
                                            ThongBao thongbaos = data.getValue(ThongBao.class);
                                            if (kiemtraNotifications.equals(thongbaos.thoigian)){
                                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                                                builder.setContentTitle(thongbaos.tieude);
                                                builder.setSmallIcon(R.drawable.notification);
                                                builder.setContentText(thongbaos.noidung);
                                                Intent resultIntent = new Intent(getApplicationContext(),thongbao.class);
                                                PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, 0);
                                                builder.setContentIntent(resultPendingIntent);
                                                builder.setAutoCancel(true);
                                                notificationManager.notify(1, builder.build());
                                                break;
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }


                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
