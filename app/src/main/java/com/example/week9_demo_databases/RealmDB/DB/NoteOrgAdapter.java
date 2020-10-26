package com.example.week9_demo_databases.RealmDB.DB;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.week9_demo_databases.R;
import com.example.week9_demo_databases.RealmDB.RealmDBActivity;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class NoteOrgAdapter extends RealmBaseAdapter<NoteOrgRlm> implements ListAdapter {

    private RealmDBActivity activity;

    private static class ViewHolder {
        TextView noteTitle;
        TextView noteDesc;
    }

    public NoteOrgAdapter(RealmDBActivity activity, OrderedRealmCollection<NoteOrgRlm> data) {
        super(data);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.noteTitle = (TextView) convertView.findViewById(R.id.titleItmLst);
            viewHolder.noteDesc = (TextView) convertView.findViewById(R.id.descItmLst);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (adapterData != null) {
            NoteOrgRlm note = adapterData.get(position);
            viewHolder.noteTitle.setText(note.getNoteTitle());
            viewHolder.noteDesc.setText(note.getNoteDescription());
        }

        return convertView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            if (adapterData != null) {
                NoteOrgRlm note = adapterData.get(position);
                activity.updateNoteName(note);
            }
        }
    };



}