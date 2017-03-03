package comkakashipham05.httpsfacebook.thpttienlu;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;

import comkakashipham05.httpsfacebook.thpttienlu.CUSTOMLIST.customThongBao;
import comkakashipham05.httpsfacebook.thpttienlu.Model.ThongBao;

public class thongbao extends AppCompatActivity {
    private DatabaseReference mDatabase;
    comkakashipham05.httpsfacebook.thpttienlu.CUSTOMLIST.customThongBao customThongBao;
    ArrayList<ThongBao> arrayList;
    ListView lv;
    Dialog dialogerror;
    TextView titleDialog,noteDialog;
    Button okError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongbao);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<>();
        connectview();
        getDulieu();
        khoitaoDialogError();
        clickListview();
    }
    private void connectview() {
        lv = (ListView) findViewById(R.id.lv);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void getDulieu(){
        mDatabase.child("Thông Báo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    ThongBao thongbao = data.getValue(ThongBao.class);
                    arrayList.add(thongbao);
                }
                customThongBao = new customThongBao(thongbao.this,R.layout.layout_thongbao,arrayList);
                lv.setAdapter(customThongBao);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void clickListview(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                titleDialog.setText(arrayList.get(position).tieude);
                noteDialog.setText(arrayList.get(position).noidung+ "\n\nThời gian đăng: "+arrayList.get(position).thoigian +"\nNgười đăng: "+arrayList.get(position).nick );
                dialogerror.show();
            }
        });
    }

    private void khoitaoDialogError(){
        dialogerror = new Dialog(thongbao.this);
        dialogerror.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogerror.setContentView(R.layout.dialog_error);
        okError = (Button) dialogerror.findViewById(R.id.okError);
        okError.setText("OK");
        titleDialog = (TextView) dialogerror.findViewById(R.id.titleDialog);
        noteDialog = (TextView) dialogerror.findViewById(R.id.noteDialog);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.9);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.9
        );
        dialogerror.getWindow().setLayout(width, height);
        okError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogerror.dismiss();

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
            try{
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            }
            catch(NoSuchMethodException e){
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.dangnhap){
            Intent intent = new Intent(thongbao.this,AdminMode.class);
            startActivity(intent);
        }

        if (id == R.id.dangki){
            Intent intent = new Intent(thongbao.this,SignUP.class);
            startActivity(intent);
        }
        if(id ==R.id.setting)
        {
            Intent intent = new Intent(thongbao.this,setting.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }



}
