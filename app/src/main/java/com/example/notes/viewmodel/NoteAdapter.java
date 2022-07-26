package com.example.notes.viewmodel;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.notes.App;
import com.example.notes.R;
import com.example.notes.model.Note;

import java.util.Calendar;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private SortedList<Note> notes;

    public NoteAdapter() {
        notes = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.id == item2.id;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(notes.get(position));
        holder.updateStrokeOut();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setItems(List<Note> noteList) {
        notes.replaceAll(noteList);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox isDone;
        View delete;
        Note note;
        boolean silentUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.note_text);
            isDone = itemView.findViewById(R.id.completed);
            delete = itemView.findViewById(R.id.image_button);

            isDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!silentUpdate) {
                        note.isDone = isChecked;
                        App.getInstance().getNoteDao().update(note);
                        updateStrokeOut();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getNoteDao().delete(note);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNoteActivity.start((Activity) itemView.getContext(), note);
                }
            });
        }

        public void bind(Note note) {
            this.note = note;
            textView.setText(note.text);
            silentUpdate = true;
            isDone.setChecked(note.isDone);
            silentUpdate = false;
        }

        private void updateStrokeOut() {
            if (note.isDone) {
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}