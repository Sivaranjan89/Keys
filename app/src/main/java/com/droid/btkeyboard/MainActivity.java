package com.droid.btkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.droid.keys.NumericKeyboard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumericKeyboard keyboard = findViewById(R.id.keyboard);
        keyboard.onKeyClicked(new NumericKeyboard.KeysClickEvent() {
            @Override
            public void onKeyClicked(String clickedKey) {
                Toast.makeText(MainActivity.this, clickedKey, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
