package com.example.notes.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.notes.App;
import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.notifications.AlarmReceiver;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editText;
    private Note note;
    private Note extraNote;
    private TimePicker timePicker;
    private int currentTime;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editText = findViewById(R.id.editText);
        timePicker = findViewById(R.id.time_picker);

        timePicker.setIs24HourView(true);

        if (getIntent().hasExtra("NOTE_EXTRA")) {
            extraNote = getIntent().getParcelableExtra("NOTE_EXTRA");
            editText.setText(extraNote.text);
        }

        currentTime = (timePicker.getHour() * 60 * 60) + (timePicker.getMinute() * 60);
        System.out.println(currentTime);
    }

    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, AddNoteActivity.class);
        if (note != null) {
            intent.putExtra("NOTE_EXTRA", note);
        }
        caller.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (editText.getText().length() > 0) {
            note = new Note();
            note.text = editText.getText().toString();
            note.isDone = false;
            note.timestamp = System.currentTimeMillis();

            if (getIntent().hasExtra("NOTE_EXTRA")) {
                note.id = extraNote.id;
                App.getInstance().getNoteDao().update(note);
            } else {
                App.getInstance().getNoteDao().insert(note);
            }
        }

        int time = (timePicker.getHour() * 60 * 60) + (timePicker.getMinute() * 60) - currentTime;
        Log.d("MyLog", Integer.toString(time));

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,  time);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_MUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        finish();
        return true;
    }
}