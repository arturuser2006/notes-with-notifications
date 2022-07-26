package com.example.notes.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="text")
    public String text;

    @ColumnInfo(name="timestamp")
    public long timestamp;

    @ColumnInfo(name="isDone")
    public boolean isDone;

    public Note() {

    }

    protected Note(Parcel in) {
        id = in.readInt();
        text = in.readString();
        timestamp = in.readLong();
        isDone = in.readByte() != 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && timestamp == note.timestamp && isDone == note.isDone && Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, timestamp, isDone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeLong(timestamp);
        dest.writeByte((byte) (isDone ? 1 : 0));
    }
}
