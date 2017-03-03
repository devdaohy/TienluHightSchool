package comkakashipham05.httpsfacebook.thpttienlu.CUSTOMLIST;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comkakashipham05.httpsfacebook.thpttienlu.Model.TKBGiaoVien;
import comkakashipham05.httpsfacebook.thpttienlu.R;

/**
 * Created by Administrator on 06/02/2017.
 */

public class CustomTKBgiaovien extends ArrayAdapter<TKBGiaoVien> {
    private Activity context;
    private int resource;
    private ArrayList<TKBGiaoVien> arr;

    public CustomTKBgiaovien(Activity context, int resource, List<TKBGiaoVien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        arr = (ArrayList<TKBGiaoVien>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_tkb_giaovien,parent,false);
        TextView tengiaovien = (TextView) convertView.findViewById(R.id.tengiaovien);
        TKBGiaoVien vitri = arr.get(position);
        tengiaovien.setText(vitri.getGiaovien());
        return convertView;
    }
}
