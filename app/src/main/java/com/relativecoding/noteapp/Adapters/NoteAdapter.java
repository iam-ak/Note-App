package com.relativecoding.noteapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.relativecoding.noteapp.Models.Note;
import com.relativecoding.noteapp.R;


public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    OnItemClickListener listener;

    public NoteAdapter() {
        super(DiFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DiFF_CALLBACK=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority()==newItem.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note note = getItem(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.priority.setText(String.valueOf(note.getPriority()));

    }



    public Note getNoteAt(int position) {
        return getItem(position);
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        private TextView title, description, priority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            priority = itemView.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }


    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
