package com.example.reviewly;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView questionText = findViewById(R.id.question_textView);
        TextView answerText = findViewById(R.id.answer_textView);
        ImageView addButton = findViewById(R.id.button_Add);

        String data = getIntent().getStringExtra("some_key2");



        questionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionText.setVisibility(View.INVISIBLE);
                answerText.setVisibility(View.VISIBLE);
            }

        });

        answerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionText.setVisibility(View.VISIBLE);
                answerText.setVisibility(View.INVISIBLE);
            }

        });

        findViewById(R.id.button_Add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // questionText.setVisibility(View.INVISIBLE);
                // answerText.setVisibility(View.INVISIBLE);
                //Intent move_back =
                 //       new Intent(MainActivity.this, AddCardActivity.class);
                //move_back.putExtra( "some_key", "some_info_to_send");
                //startActivityForResult(move_back, 100);

                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent,100);
            }
        });




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            TextView questionText = findViewById(R.id.question_textView);
            TextView answerText = findViewById(R.id.answer_textView);
            String string1 = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String string2 = data.getExtras().getString("string2");

            //Log commands
            Log.d("MainActivity", data.getExtras().getString("string2"));

            //Puts strings back into Textviews
            questionText.setText(string1);
            answerText.setText(string2);
        }
    }


}