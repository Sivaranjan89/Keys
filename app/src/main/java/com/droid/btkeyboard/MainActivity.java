package com.droid.btkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.droid.keys.NumericKeyboard;
import com.droid.keys.QwertyKeyboard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QwertyKeyboard keyboard = findViewById(R.id.keyboard);
        keyboard.onKeyClicked(new QwertyKeyboard.KeysClickEvent() {
            @Override
            public void onKeyClicked(String clickedKey) {
                Toast.makeText(MainActivity.this, clickedKey, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
