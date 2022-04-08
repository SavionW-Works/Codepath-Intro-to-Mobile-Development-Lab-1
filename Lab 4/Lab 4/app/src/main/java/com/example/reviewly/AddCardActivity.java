package com.example.reviewly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;


public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        TextView questionText = findViewById(R.id.question_textView);
        TextView answerText = findViewById(R.id.answer_textView);
        ImageView closeButton = findViewById(R.id.button_Close);
        ImageView saveButton = findViewById(R.id.button_Save);
        String data = getIntent().getStringExtra("some_key");



        findViewById(R.id.button_Save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {









                Intent data = new Intent(); // create a new Intent, this is where we will put our data

                data.putExtra("user_question",((EditText) findViewById(R.id.question_Add)).getText().toString() ); // puts one string into the Intent, with the key as 'string1'
                data.putExtra("user_answer", ((EditText) findViewById(R.id.answer_Add)).getText().toString()); // puts another string into the Intent, with the key as 'string2
                setResult(RESULT_OK, data); // set result code and bundle data for response
                Log.d("AddCardActivity", data.getExtras().getString("user_answer"));
                //Starts new card activity now that card values have been edited
               //Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
               //AddCardActivity.this.startActivityForResult(intent, 100); //Depreciated for some reason
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);


            }


        });

        findViewById(R.id.button_Close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                //Animation technically works, but main screen is black until it finishes
            }


        });


    }


}