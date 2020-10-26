package com.example.week9_demo_databases.SQLiteDB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.week9_demo_databases.R;
import com.example.week9_demo_databases.SQLiteDB.DB.NoteOrganizer;

import java.util.ArrayList;
import java.util.List;


public class NoteOrgAdapter extends ArrayAdapter<NoteOrganizer> {
    private final Context context;
    private final List<NoteOrganizer> values;


    public NoteOrgAdapter(Context context, List<NoteOrganizer> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_layout, parent, false);
        TextView textViewTitle = (TextView) rowView.findViewById(R.id.titleItmLst);
        TextView textViewDesc = (TextView) rowView.findViewById(R.id.descItmLst);

        textViewTitle.setText(values.get(position).getNoteTitle());
        textViewDesc.setText(values.get(position).getNoteDescription());

        return rowView;
    }


}
