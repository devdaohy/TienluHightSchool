package comkakashipham05.httpsfacebook.thpttienlu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;

import comkakashipham05.httpsfacebook.thpttienlu.Model.ACCOUNT;

public class SignUP extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView titleDialog,noteDialog;
    EditText username,password,code;
    Button signup,okError;
    ACCOUNT account;
    Dialog dialogerror;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        connectview();
        signup();

    }

    private void signup() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = username.getText().toString();
                final String pass = password.getText().toString();
                final String textcode = code.getText().toString();


                mDatabase.child("Code").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean kiemtracode = false;
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            if (data.getValue().toString().equals(textcode))
                            {
                                account = new ACCOUNT(user,pass);
                                mDatabase.child("ACCOUNT").push().setValue(account);
                                khoitaoDialogError();
                                noteDialog.setText("Xin chúc mừng, bạn đã đăng kí tài khoản thành công\nTên tài khoản là: "+user+"\nMật khẩu là: "+pass+"\n\nBây giờ bạn có thể bắt đầu đăng nhập với tài khoản của mình");
                                dialogerror.show();
                                kiemtracode = true;
                            }
                            if (kiemtracode == true) {break;}
                        }
                        if (kiemtracode== false) { Toast.makeText(SignUP.this, "Lỗi code, mời bạn nhập lại", Toast.LENGTH_LONG).show(); }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


    }

    private void connectview() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        code = (EditText) findViewById(R.id.code);
        signup = (Button) findViewById(R.id.btnSignUp);
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
            Intent intent = new Intent(SignUP.this,AdminMode.class);
            startActivity(intent);
        }

        if (id == R.id.dangki){
            Intent intent = new Intent(SignUP.this,SignUP.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    private void khoitaoDialogError(){
        dialogerror = new Dialog(SignUP.this);
        dialogerror.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogerror.setContentView(R.layout.dialog_error);
        okError = (Button) dialogerror.findViewById(R.id.okError);
        okError.setText("Quay lại đăng nhập");
        titleDialog = (TextView) dialogerror.findViewById(R.id.titleDialog);
        titleDialog.setText("Đăng kí thành công");
        noteDialog = (TextView) dialogerror.findViewById(R.id.noteDialog);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.8);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.5);
        dialogerror.getWindow().setLayout(width, height);
        okError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(SignUP.this,AdminMode.class);
               startActivity(intent);
               finish();
                dialogerror.dismiss();

            }
        });

    }

}
