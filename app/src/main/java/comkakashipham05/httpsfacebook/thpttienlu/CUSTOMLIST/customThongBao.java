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

import comkakashipham05.httpsfacebook.thpttienlu.Model.ThongBao;
import comkakashipham05.httpsfacebook.thpttienlu.R;

/**
 * Created by Administrator on 01/02/2017.
 */

public class customThongBao extends ArrayAdapter<ThongBao> {

    private Activity context;
    private int resource;
    ArrayList<ThongBao> arrThongBao;
    public customThongBao(Activity context, int resource, List<ThongBao> objects) {

        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        arrThongBao = (ArrayList<ThongBao>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_thongbao,parent,false);
        ThongBao vitri = arrThongBao.get(position);
        TextView tieude = (TextView) convertView.findViewById(R.id.tieude);
        TextView nick = (TextView) convertView.findViewById(R.id.nick);
        TextView shortText = (TextView) convertView.findViewById(R.id.shortText);
        TextView thoigian = (TextView) convertView.findViewById(R.id.thoigian);

        tieude.setText(vitri.tieude);
        nick.setText(vitri.nick);
        shortText.setText(vitri.noidung);
        thoigian.setText(vitri.thoigian);
        return convertView;
    }
}
