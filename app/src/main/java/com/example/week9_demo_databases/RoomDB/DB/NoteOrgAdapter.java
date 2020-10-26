package com.example.week9_demo_databases.RoomDB.DB;


import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

import com.example.week9_demo_databases.R;
import com.example.week9_demo_databases.RoomDB.UpdateNoteActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NoteOrgAdapter  extends RecyclerView.Adapter<NoteOrgAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<NoteOrgRoom> taskList;

    public NoteOrgAdapter(Context mCtx, List<NoteOrgRoom> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_notes_room, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        NoteOrgRoom t = taskList.get(position);
        holder.textViewTask.setText(t.getNoteTitle());
        holder.textViewDesc.setText(t.getNoteDesc());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;

        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            NoteOrgRoom note = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateNoteActivity.class);
            intent.putExtra("Note", note);

            mCtx.startActivity(intent);
        }
    }
}