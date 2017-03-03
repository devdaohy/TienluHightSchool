package comkakashipham05.httpsfacebook.thpttienlu.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

import comkakashipham05.httpsfacebook.thpttienlu.CUSTOMLIST.customThongBao;
import comkakashipham05.httpsfacebook.thpttienlu.Model.ThongBao;
import comkakashipham05.httpsfacebook.thpttienlu.R;


public class Thongbao extends Fragment {
    private DatabaseReference mDatabase;
    comkakashipham05.httpsfacebook.thpttienlu.CUSTOMLIST.customThongBao customThongBao;
    ArrayList<ThongBao> arrayList;
    ListView lv;
    Dialog dialogerror;
    TextView titleDialog,noteDialog;
    Button okError;


    public Thongbao() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongbao, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<>();
        connectview();
        getDulieu();
        khoitaoDialogError();
        clickListview();
    }

    private void connectview() {
        lv = (ListView) getView().findViewById(R.id.lv);
        AdView mAdView = (AdView) getView().findViewById(R.id.adView);
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
            customThongBao = new customThongBao(getActivity(),R.layout.layout_thongbao,arrayList);
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
        dialogerror = new Dialog(getActivity());
        dialogerror.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogerror.setContentView(R.layout.dialog_error);
        okError = (Button) dialogerror.findViewById(R.id.okError);
        okError.setText("OK");
        titleDialog = (TextView) dialogerror.findViewById(R.id.titleDialog);
        noteDialog = (TextView) dialogerror.findViewById(R.id.noteDialog);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.9);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.7);
        dialogerror.getWindow().setLayout(width, height);
        okError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogerror.dismiss();

            }
        });


    }



}
