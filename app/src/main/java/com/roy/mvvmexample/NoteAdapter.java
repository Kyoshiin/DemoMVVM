package com.roy.mvvmexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Implementing ListAdapter of RecycleView instead of RecycleView.Adapter
 * for knowing what item has been changed for notifyItemChanged(int)
 *
 * list.get(pos) --> getItem(pos)
 * provide comparison logic via DIFFUtil
 */
public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteHolder> {

    private OnItemClickListner listner;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    /**
     * Comparison logic where item has been changed
     * in the list
     */
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        //get data from java objects into the NotHolder
        Note currentnote = getItem(position);
        holder.textViewTitle.setText(currentnote.getTitle());
        holder.textViewPriority.setText(String.valueOf(currentnote.getPriority()));
        holder.textViewDesc.setText(currentnote.getDescription());
    }


    /**
     * To update the notes from livedata
     * from MainActivity
     */


    //for deleting node va swiping
    public Note getNoteAt(int pos) {
        return getItem(pos);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDesc;
        private TextView textViewPriority;


        public NoteHolder(@NonNull View itemView) {
            super(itemView); // itemView -> cardView

            textViewTitle = itemView.findViewById(R.id.text_view_tittle);
            textViewDesc = itemView.findViewById(R.id.text_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (listner != null && pos != RecyclerView.NO_POSITION) // for deld pos
                        listner.onItemClick(getItem(pos));
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(Note note);
    }

    /**
     * for reference of clicklistner
     * use the listner variable reference to call 'onItemClick'
     * and forward the note obj to the class implementing the interface
     */
    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;

    }
}
