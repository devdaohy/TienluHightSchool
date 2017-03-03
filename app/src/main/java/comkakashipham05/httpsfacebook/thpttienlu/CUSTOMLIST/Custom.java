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

import comkakashipham05.httpsfacebook.thpttienlu.Model.TKB;
import comkakashipham05.httpsfacebook.thpttienlu.R;

/**
 * Created by Administrator on 30/01/2017.
 */
public class Custom extends ArrayAdapter<TKB> {
    private Activity context;
    private int resource;
    private ArrayList<TKB> arr;


    public Custom(Activity context, int resource, List<TKB> objects) {
        super(context, resource, objects);

        this.context= context;
        this.resource=resource;
        arr = (ArrayList<TKB>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_listview,parent,false);
        TextView txtclass = (TextView) convertView.findViewById(R.id.txtclass);
        TextView txtchunhiem = ( TextView) convertView.findViewById(R.id.txtchunhiem);
        TKB vitri = arr.get(position);
        txtclass.setText(vitri.Lop);
        txtchunhiem.setText(vitri.Chunhiem);
        return convertView;
    }
}