package comkakashipham05.httpsfacebook.thpttienlu.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import comkakashipham05.httpsfacebook.thpttienlu.CUSTOMLIST.Custom;
import comkakashipham05.httpsfacebook.thpttienlu.Model.TKB;
import comkakashipham05.httpsfacebook.thpttienlu.R;


public class Thoikhoabieu extends Fragment {
    private DatabaseReference mDatabase;
    ListView lv;
    Custom custom;
    ArrayList<TKB> mang;
    TKB tkb;
    Button dialogOk,btnTimkiem,okError,hocsinhmode;
    EditText edittimkiem;
    Dialog dialogtkb,dialogerror;
    TextView dialogThu2,dialogThu3,dialogThu4,dialogThu5,dialogThu6,dialogThu7,dialogLop,tkbStart
    ,titleDialog,noteDialog;
    int dem;

    public Thoikhoabieu() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thoikhoabieu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mang = new ArrayList<>();
        connectview();
        khoitaoDialog();

        clicklistview();
        sukienclick();
        firebase();
    }



    private void firebase(){
        mDatabase.child("TKB Start").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tkbStart.setText("Áp dụng từ ngày : " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("TKBHocSinh").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mang.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    tkb = data.getValue(TKB.class);
                    mang.add(tkb);
                }
                custom = new Custom(getActivity(),R.layout.layout_listview,mang);
                lv.setAdapter(custom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void connectview() {
        lv = (ListView) getView().findViewById(R.id.lv);
        edittimkiem = (EditText) getView().findViewById(R.id.edittimkiem);
        tkbStart = (TextView) getView().findViewById(R.id.tkbStart);
        btnTimkiem = (Button) getView().findViewById(R.id.btnTimkiem);
        hocsinhmode = (Button) getView().findViewById(R.id.hocsinhmode);
    }

    private void clicklistview(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogLop.setText(" Thời Khóa Biểu Lớp : "+ mang.get(position).Lop);
                dialogThu2.setText(mang.get(position).thu2);
                dialogThu3.setText(mang.get(position).thu3);
                dialogThu4.setText(mang.get(position).thu4);
                dialogThu5.setText(mang.get(position).thu5);
                dialogThu6.setText(mang.get(position).thu6);
                dialogThu7.setText(mang.get(position).thu7);
                dialogtkb.show();
            }
        });
    }

    private void khoitaoDialog(){
        dialogtkb = new Dialog(getActivity());
        dialogtkb.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogtkb.setContentView(R.layout.dialog_tkb);
        dialogThu2 = (TextView) dialogtkb.findViewById(R.id.dialogThu2);
        dialogThu3 = (TextView) dialogtkb.findViewById(R.id.dialogThu3);
        dialogThu4 = (TextView) dialogtkb.findViewById(R.id.dialogThu4);
        dialogThu5 = (TextView) dialogtkb.findViewById(R.id.dialogThu5);
        dialogThu6 = (TextView) dialogtkb.findViewById(R.id.dialogThu6);
        dialogThu7 = (TextView) dialogtkb.findViewById(R.id.dialogThu7);
        dialogThu4 = (TextView) dialogtkb.findViewById(R.id.dialogThu4);
        dialogLop = (TextView) dialogtkb.findViewById(R.id.dialogLop);
        AdView mAdView = (AdView) dialogtkb.findViewById(R.id.adsHocsinh);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        dialogOk = (Button) dialogtkb.findViewById(R.id.dialogOk);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogtkb.dismiss();
            }
        });
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.8);
        dialogtkb.getWindow().setLayout(width, height);




    }

    private void sukienclick(){
        btnTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dem=0;
                final String keytimkiem = edittimkiem.getText().toString();
                mDatabase.child("TKBHocSinh").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            tkb = data.getValue(TKB.class);
                            if (keytimkiem.equalsIgnoreCase(tkb.Lop)) {
                                dialogLop.setText(" Thời Khóa Biểu Lớp : " + tkb.Lop);
                                dialogThu2.setText(tkb.thu2);
                                dialogThu3.setText(tkb.thu3);
                                dialogThu4.setText(tkb.thu4);
                                dialogThu5.setText(tkb.thu5);
                                dialogThu6.setText(tkb.thu6);
                                dialogThu7.setText(tkb.thu7);
                                dialogtkb.show();
                                dem=dem +1;
                            }
                        }
                        if (dem==0) {
                            khoitaoDialogError();
                            noteDialog.setText("Nhập sai tên lớp \nVí dụ: Bạn muốn tìm thời khóa biểu lớp 12A3 thì hãy nhập '12a3' hoặc '12A3' vào ô tìm kiếm rồi ấn vào nút tìm kiếm bên cạnh \nMời bạn nhập lại...");
                            titleDialog.setText("Error: Keyword Wrong");
                            okError.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogerror.dismiss();
                                }
                            });
                            dialogerror.show();

                        }
                    }

                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        hocsinhmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ThoikhoabieuGiaovien());

            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentView, fragment,"tag");
        fragmentTransaction.commit();

    }

    private void khoitaoDialogError(){
        dialogerror = new Dialog(getActivity());
        dialogerror.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogerror.setContentView(R.layout.dialog_error);
        okError = (Button) dialogerror.findViewById(R.id.okError);
        titleDialog = (TextView) dialogerror.findViewById(R.id.titleDialog);
        noteDialog = (TextView) dialogerror.findViewById(R.id.noteDialog);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.8);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.5);
        dialogerror.getWindow().setLayout(width, height);

    }


}
