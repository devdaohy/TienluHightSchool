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

import comkakashipham05.httpsfacebook.thpttienlu.CUSTOMLIST.CustomTKBgiaovien;
import comkakashipham05.httpsfacebook.thpttienlu.Model.TKBGiaoVien;
import comkakashipham05.httpsfacebook.thpttienlu.R;

import static comkakashipham05.httpsfacebook.thpttienlu.R.id.dialogLop;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThoikhoabieuGiaovien extends Fragment {
    private DatabaseReference mDatabase;
    ListView lvv;
    ArrayList<TKBGiaoVien> arraylist;
    String []mang=new String[1000];
    String keytimkiem;
    Dialog dialog,dialogerror;
    TextView dialogThu2,dialogThu3,dialogThu4,dialogThu5,dialogThu6,dialogThu7,dialogGiaovien,textthem
    ,titleDialog,noteDialog,tkbStart;
    Button dialogOk,giaovienmode,btnTimkiem,okError;
    EditText edittimkiem;
    int dem;


    public ThoikhoabieuGiaovien() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thoikhoabieu_giaovien, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        arraylist=new ArrayList<>();
        connectView();
        khoitaodialog();
        readFirebase();
        clicklistview();
        click();
    }

    private void click() {
        giaovienmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Thoikhoabieu());
            }
        });

        btnTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dem=0;
                keytimkiem = edittimkiem.getText().toString();
                mDatabase.child("TKBGiaoVien").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            TKBGiaoVien tkb = data.getValue(TKBGiaoVien.class);
                            if (keytimkiem.equalsIgnoreCase(tkb.getGiaovien())) {
                                dialogGiaovien.setText("Thời Khóa Biểu Của Giáo Viên: "+tkb.getGiaovien());
                                dialogThu2.setText(tkb.getThu2());
                                dialogThu3.setText(tkb.getThu3());
                                dialogThu4.setText(tkb.getThu4());
                                dialogThu5.setText(tkb.getThu5());
                                dialogThu6.setText(tkb.getThu6());
                                dialogThu7.setText(tkb.getThu7());
                                textthem.setText(tkb.getNoteGiaovien());
                                dialog.show();
                                dem=1;
                            }
                        }
                        if (dem==0) {
                            khoitaoDialogError();
                            noteDialog.setText("Nhập sai tên giáo viên \nHãy kiểm tra lại tên viết tắt của giáo viên cần tìm thời khóa biểu đã đúng chưa ! \nMời bạn nhập lại...");
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
    }


    private void readFirebase(){

        mDatabase.child("TKB Start").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tkbStart.setText("Áp dụng từ ngày : " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("TKBGiaoVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arraylist.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    TKBGiaoVien tkbs = data.getValue(TKBGiaoVien.class);
                    arraylist.add(tkbs);
                }
                CustomTKBgiaovien customs = new CustomTKBgiaovien(getActivity(),R.layout.layout_tkb_giaovien,arraylist);
                lvv.setAdapter(customs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void clicklistview(){
        lvv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogGiaovien.setText("Thời Khóa Biểu Của Giáo Viên: "+arraylist.get(position).getGiaovien());
                dialogThu2.setText(arraylist.get(position).getThu2());
                dialogThu3.setText(arraylist.get(position).getThu3());
                dialogThu4.setText(arraylist.get(position).getThu4());
                dialogThu5.setText(arraylist.get(position).getThu5());
                dialogThu6.setText(arraylist.get(position).getThu6());
                dialogThu7.setText(arraylist.get(position).getThu7());
                textthem.setText(arraylist.get(position).getNoteGiaovien());
                dialog.show();
            }
        });
    }

    private void connectView() {
        lvv = (ListView) getView().findViewById(R.id.lv);
        giaovienmode = (Button) getView().findViewById(R.id.giaovienmode);
        btnTimkiem = (Button) getView().findViewById(R.id.btnTimkiem);
        edittimkiem = (EditText) getView().findViewById(R.id.edittimkiem);
        tkbStart = (TextView) getView().findViewById(R.id.tkbStart);
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentView, fragment,"tag");
        fragmentTransaction.commit();
    }


    private void khoitaodialog(){
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tkb_giaovien);
        dialogThu2 = (TextView) dialog.findViewById(R.id.dialogThu2);
        dialogThu3 = (TextView) dialog.findViewById(R.id.dialogThu3);
        dialogThu4 = (TextView) dialog.findViewById(R.id.dialogThu4);
        dialogThu5 = (TextView) dialog.findViewById(R.id.dialogThu5);
        dialogThu6 = (TextView) dialog.findViewById(R.id.dialogThu6);
        dialogThu7 = (TextView) dialog.findViewById(R.id.dialogThu7);
        dialogThu4 = (TextView) dialog.findViewById(R.id.dialogThu4);
        dialogGiaovien = (TextView) dialog.findViewById(dialogLop);
        textthem = (TextView) dialog.findViewById(R.id.textthem) ;
        AdView mAdView = (AdView) dialog.findViewById(R.id.adsGiaovien);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        dialogOk = (Button) dialog.findViewById(R.id.dialogOk);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.8);
        dialog.getWindow().setLayout(width, height);

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
