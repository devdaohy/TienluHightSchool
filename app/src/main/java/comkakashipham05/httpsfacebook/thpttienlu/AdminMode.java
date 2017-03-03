package comkakashipham05.httpsfacebook.thpttienlu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;

import comkakashipham05.httpsfacebook.thpttienlu.Model.ACCOUNT;
import comkakashipham05.httpsfacebook.thpttienlu.Model.ThongBao;

public class AdminMode extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ArrayList<ThongBao> arrayList;
    Dialog dialogerror;
    Button btnCreate,btnLogin,okError;
    EditText username,password;
    TextView titleDialog,noteDialog;
    String user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminode);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<>();
        connectview();
        click();



    }

    private void connectview() {
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    private void click() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMode.this, SignUP.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                mDatabase.child("ACCOUNT").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean kiemtraUser = false;
                        boolean kiemtra = false;

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            ACCOUNT account = data.getValue(ACCOUNT.class);
                            if (user.equals(account.user)) {
                                kiemtraUser = true;
                                if (pass.equals(account.pass)) {
                                    kiemtra = true;
                                    Intent intent = new Intent(AdminMode.this, AdminAction.class);
                                    intent.putExtra("USER", user);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            if (kiemtra == true) {
                                break;
                            }
                        }

                        if (kiemtraUser == false) {
                            khoitaoDialogError();
                            titleDialog.setText("Sai tài khoản");
                            noteDialog.setText("Bạn vừa nhập sai tên tài khoản của mình, xin mời bạn nhập lại !");
                            dialogerror.show();
                        } else {
                            if (kiemtra == false) {
                                khoitaoDialogError();
                                titleDialog.setText("Sai mật khẩu");
                                noteDialog.setText("Bạn vừa nhập sai mật khẩu của mình, xin mời bạn nhập lại !");
                                dialogerror.show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
            Intent intent = new Intent(AdminMode.this,AdminMode.class);
            startActivity(intent);
        }

        if (id == R.id.dangki){
            Intent intent = new Intent(AdminMode.this,SignUP.class);
            startActivity(intent);
        }
        if(id ==R.id.setting)
        {
            Intent intent = new Intent(AdminMode.this,setting.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    private void khoitaoDialogError(){
        dialogerror = new Dialog(AdminMode.this);
        dialogerror.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogerror.setContentView(R.layout.dialog_error);
        okError = (Button) dialogerror.findViewById(R.id.okError);
        titleDialog = (TextView) dialogerror.findViewById(R.id.titleDialog);
        noteDialog = (TextView) dialogerror.findViewById(R.id.noteDialog);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.8);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.4);
        dialogerror.getWindow().setLayout(width, height);
        okError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogerror.dismiss();

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
