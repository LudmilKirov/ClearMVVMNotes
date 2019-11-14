package com.example.clearmvvmnotes.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clearmvvmnotes.R;
import com.example.clearmvvmnotes.model.Note;

import java.util.ArrayList;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteHolder> {
    private static final String TAG = "NoteAdapter";
    private OnItemClickListener listener;
    private ArrayList<Note> mNotes = new ArrayList<>();

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    &&oldItem.getPriority()==newItem.getPriority();
        }
    };

    //Create and return NoteHolder
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    //Give the proper attributes
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewTile.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.getTextViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    public Note getNoteAt(int position){
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewTile;
        private TextView textViewDescription;
        private TextView getTextViewPriority;

        OnNoteListener mOnNoteListener;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTile= itemView.findViewById(R.id.text_view_title);
            getTextViewPriority=itemView.findViewById(R.id.text_view_priority);
            textViewDescription=itemView.findViewById(R.id.text_view_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(mOnNoteListener != null &&  position != RecyclerView.NO_POSITION) {
                        mOnNoteListener.onNoteClick(position);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            mOnNoteListener.onNoteClick(getAdapterPosition());
        }

    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    public  interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
