package com.javaproject.venbakural;

import android.content.Context;
import android.media.MediaPlayer;

public class VenbaAudioMp3 {
    public static final String[] notes = {
            "a0", "bb0", "b0", "c1", "db1", "d1", "eb1", "e1", "f1", "gb1", "g1", "ab1", "a1", "bb1", "b1",
            "c2", "db2", "d2", "eb2", "e2", "f2", "gb2", "g2", "ab2", "a2", "bb2", "b2", "c3", "db3", "d3",
            "eb3", "e3", "f3", "gb3", "g3", "ab3", "a3", "bb3", "b3", "c4", "db4", "d4", "eb4", "e4", "f4",
            "gb4", "g4", "ab4", "a4", "bb4", "b4", "c5", "db5", "d5", "eb5", "e5", "f5", "gb5", "g5", "ab5",
            "a5", "bb5", "b5", "c6", "db6", "d6", "eb6", "e6", "f6", "gb6", "g6", "ab6", "a6", "bb6", "b6",
            "c7", "db7", "d7", "eb7", "e7", "f7", "gb7", "g7", "ab7", "a7", "bb7", "b7", "c8"
    };

    // Method to get the resource ID based on note name
    private int getRawResourceId(Context context, String note) {
        return context.getResources().getIdentifier("note_" + note, "raw", context.getPackageName());
    }

    // Method to play the audio
    public void playNote(Context context, String note) {
        int resId = getRawResourceId(context, note);
        if (resId != 0) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, resId);
            mediaPlayer.start();
            // Release MediaPlayer when playback is complete
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        } else {
            // Handle error (e.g., invalid note or missing resource)
            System.out.println("Note not found: " + note);
        }
    }
}
