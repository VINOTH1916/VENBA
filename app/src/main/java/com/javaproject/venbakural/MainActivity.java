package com.javaproject.venbakural;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Musical note strings
    String[] strings = {
            "A0", "Bb0", "B0", "C1", "Db1", "D1", "Eb1", "E1", "F1", "Gb1", "G1", "Ab1", "A1", "Bb1", "B1",
            "C2", "Db2", "D2", "Eb2", "E2", "F2", "Gb2", "G2", "Ab2", "A2", "Bb2", "B2", "C3", "Db3", "D3",
            "Eb3", "E3", "F3", "Gb3", "G3", "Ab3", "A3", "Bb3", "B3", "C4", "Db4", "D4", "Eb4", "E4", "F4",
            "Gb4", "G4", "Ab4", "A4", "Bb4", "B4", "C5", "Db5", "D5", "Eb5", "E5", "F5", "Gb5", "G5", "Ab5",
            "A5", "Bb5", "B5", "C6", "Db6", "D6", "Eb6", "E6", "F6", "Gb6", "G6", "Ab6", "A6", "Bb6", "B6",
            "C7", "Db7", "D7", "Eb7", "E7", "F7", "Gb7", "G7", "Ab7", "A7", "Bb7", "B7", "C8"
    };

    // Audio file resource IDs (ensure audio files are named like "a0.wav", "bb0.wav", etc., in res/raw)
    int[] audioFiles = {
            R.raw.a0, R.raw.bb0, R.raw.b0, R.raw.c1, R.raw.db1, R.raw.d1, R.raw.eb1, R.raw.e1, R.raw.f1, R.raw.gb1, R.raw.g1, R.raw.ab1, R.raw.a1, R.raw.bb1, R.raw.b1,
            R.raw.c2, R.raw.db2, R.raw.d2, R.raw.eb2, R.raw.e2, R.raw.f2, R.raw.gb2, R.raw.g2, R.raw.ab2, R.raw.a2, R.raw.bb2, R.raw.b2, R.raw.c3, R.raw.db3, R.raw.d3,
            R.raw.eb3, R.raw.e3, R.raw.f3, R.raw.gb3, R.raw.g3, R.raw.ab3, R.raw.a3, R.raw.bb3, R.raw.b3, R.raw.c4, R.raw.db4, R.raw.d4, R.raw.eb4, R.raw.e4, R.raw.f4,
            R.raw.gb4, R.raw.g4, R.raw.ab4, R.raw.a4, R.raw.bb4, R.raw.b4, R.raw.c5, R.raw.db5, R.raw.d5, R.raw.eb5, R.raw.e5, R.raw.f5, R.raw.gb5, R.raw.g5, R.raw.ab5,
            R.raw.a5, R.raw.bb5, R.raw.b5, R.raw.c6, R.raw.db6, R.raw.d6, R.raw.eb6, R.raw.e6, R.raw.f6, R.raw.gb6, R.raw.g6, R.raw.ab6, R.raw.a6, R.raw.bb6, R.raw.b6,
            R.raw.c7, R.raw.db7, R.raw.d7, R.raw.eb7, R.raw.e7, R.raw.f7, R.raw.gb7, R.raw.g7, R.raw.ab7, R.raw.a7, R.raw.bb7, R.raw.b7, R.raw.c8
    };


    private EditText kuralNumberEditText;
    private EditText file1EditText, file0EditText, fileMinus1EditText;
    private TextView kuralTextView, venbaTextView,mathiraiTextView, binarySegmentTextView, resultTextView, patternTextView;
    private Button showKuralButton;
    private List<String> kuralsList;
    // Add a new field for the playback speed EditText
    private EditText speedEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        kuralNumberEditText = findViewById(R.id.kuralNumberEditText);
        file1EditText = findViewById(R.id.file1EditText);
        file0EditText = findViewById(R.id.file0EditText);
        fileMinus1EditText = findViewById(R.id.fileMinus1EditText);
        speedEditText = findViewById(R.id.speedEditText);  // Initialize the speed input
        kuralTextView = findViewById(R.id.kuralTextView);
        mathiraiTextView = findViewById(R.id.mathiraiTextView);
        binarySegmentTextView = findViewById(R.id.binarySegmentTextView);
        resultTextView = findViewById(R.id.resultTextView);
        patternTextView = findViewById(R.id.patternTextView);
        showKuralButton = findViewById(R.id.showKuralButton);
        venbaTextView = findViewById(R.id.venbaTextView);

        // Load the Kurals
        kuralsList = loadKuralsFromAssets();
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Play the corresponding audio file
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, audioFiles[position % audioFiles.length]);
                mediaPlayer.start();
            }
        });



    }

    // Button click handler
    public void showKural(View view) {
        String input = kuralNumberEditText.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a Kural number or Kural text", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the input is a valid Kural number (integer)
        try {
            int kuralNumber = Integer.parseInt(input);

            if (kuralNumber > 0 && kuralNumber <= kuralsList.size()) {
                String selectedKural = kuralsList.get(kuralNumber - 1);
                kuralTextView.setText(selectedKural);
                processVenba(selectedKural);
            } else {
                Toast.makeText(this, "Invalid Kural number", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            // If it's not a valid number, treat it as a string and check if it matches a Kural
                kuralTextView.setText(input);
                processVenba(input);
        }
    }


    // Process Venba analysis
    private void processVenba(String selectedKural) {
        Venba venba = new Venba();
        DFA dfa = new DFA();
        ArrayList<Integer> pattern = new ArrayList<>();

        List<Double> mathiraiValues = venba.getMathiraiValues(selectedKural);
        mathiraiTextView.setText("Mathirai values: " + mathiraiValues);

        List<String> niraiNerAnalysis = new ArrayList<>();
        List<Double> tempValues = new ArrayList<>();

        for (Double value : mathiraiValues) {
            if (value == -1.0) {
                venba.applyNiraiNerRules(tempValues, niraiNerAnalysis, venba);
                niraiNerAnalysis.add(" ");
                tempValues.clear();
            } else {
                tempValues.add(value);
            }
        }

        if (!tempValues.isEmpty()) {
            venba.applyNiraiNerRules(tempValues, niraiNerAnalysis, venba);
        }

        binarySegmentTextView.setText("Nirai Ner Analysis: " + String.join("", niraiNerAnalysis));

        List<String> nerniraiList = new ArrayList<>();
        nerniraiList.clear();
        for (String segment : venba.nernirai) {
            if (segment != null && !segment.isEmpty()) {
                nerniraiList.add(segment);
            }
        }












        patternTextView.setText("Nernirai List: " + nerniraiList.toString());


        String venbaString = venba.printVenbaWords(nerniraiList);

        // Find the TextView from the layout


        // Set the resulting string in the TextView
        venbaTextView.setText(venbaString);

        if (Venba.isVenba(nerniraiList) && dfa.venbaDFA(nerniraiList)) {
            resultTextView.setText("This is a Venba. String Accepted.");

            for (String str : nerniraiList) {
                for (char ch : str.toCharArray()) {
                    pattern.add(Character.getNumericValue(ch));
                }
                pattern.add(-1);
            }
            pattern.remove(pattern.size() - 1);

            try {
                String file1 = file1EditText.getText().toString().trim();
                String file0 = file0EditText.getText().toString().trim();
                String fileMinus1 = fileMinus1EditText.getText().toString().trim();

                // Get playback speed from user input, default to 1.0 if not provided
                String speedInput = speedEditText.getText().toString().trim();
                float speed = 1.0f;  // Default speed

                if (!speedInput.isEmpty()) {
                    try {
                        speed = Float.parseFloat(speedInput);
                        if (speed <= 0) {
                            Toast.makeText(this, "Speed must be greater than 0", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Invalid speed input", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                for (int value : pattern) {
                    playSound(value, file1, file0, fileMinus1, speed);
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid note values", Toast.LENGTH_SHORT).show();
            }
        } else {
            resultTextView.setText("This is not a Venba. String not Accepted.");
        }
    }

    // Play sound based on value and user-defined speed
    private void playSound(int value, String file1, String file0, String fileMinus1, float speed) {
        MediaPlayer mediaPlayer = null;
        try {
            // Determine which file to play based on the value
            String fileToPlay = null;
            switch (value) {
                case 1:
                    fileToPlay = file1;
                    break;
                case 0:
                    fileToPlay = file0;
                    break;
                case -1:
                    fileToPlay = fileMinus1;
                    break;
            }

            if (fileToPlay != null) {
                // Delay of 300ms before playing the sound
                Thread.sleep(250); // 300ms delay

                // Create the MediaPlayer instance and load the resource
                mediaPlayer = MediaPlayer.create(this, getResources().getIdentifier(fileToPlay, "raw", getPackageName()));
                if (mediaPlayer != null) {
                    // Set playback speed based on user input
                    mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));

                    // Start the sound and release the MediaPlayer when done
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mp -> mp.release());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error with delay", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error playing sound", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    private List<String> loadKuralsFromAssets() {
        List<String> kurals = new ArrayList<>();
        try (InputStream is = getAssets().open("kurals.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                kurals.add(line.replace("$", " "));
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error loading Kurals", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return kurals;
    }
}



/*private void playSound(int value, String file1, String file0, String fileMinus1, float speed) {
    final MediaPlayer mediaPlayer = new MediaPlayer();
    try {
        int resourceId = 0;
        switch (value) {
            case 1:
                resourceId = getResources().getIdentifier(file1, "raw", getPackageName());
                break;
            case 0:
                resourceId = getResources().getIdentifier(file0, "raw", getPackageName());
                break;
            case -1:
                resourceId = getResources().getIdentifier(fileMinus1, "raw", getPackageName());
                break;
        }

        if (resourceId != 0) {
            // Use a Runnable to handle the 300ms delay
            Runnable playAfterDelay = new Runnable() {
                @Override
                public void run() {
                    try {
                        mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://" + getPackageName() + "/raw/" + resourceId));
                        mediaPlayer.prepare();
                        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(mp -> mp.release());
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Error playing sound", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            };

            // Post the runnable with a delay of 300ms
            new Handler().postDelayed(playAfterDelay, 300);
        }
    } catch (Exception e) {
        Toast.makeText(getApplicationContext(), "Error playing sound", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
}
*/