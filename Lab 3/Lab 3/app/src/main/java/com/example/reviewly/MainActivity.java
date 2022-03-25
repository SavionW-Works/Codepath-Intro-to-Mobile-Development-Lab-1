package com.example.reviewly;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    //Database for flashcards
    FlashcardDatabase flashcardDatabase;

    //List for all flashcards
    List<Flashcard> allFlashcards;

    int cardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        //Check if database is empty before displaying

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        TextView questionText = findViewById(R.id.question_textView);
        TextView answerText = findViewById(R.id.answer_textView);

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.question_textView)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.answer_textView)).setText(allFlashcards.get(0).getAnswer());
        }

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

        findViewById(R.id.button_Next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards == null || allFlashcards.size() == 0) {
                    return;
                }

                cardIndex += 1;

                if (cardIndex >= allFlashcards.size()) {
                    Snackbar.make(view, "End of Cards!",Snackbar.LENGTH_SHORT).show();
                    cardIndex = 0;
                }

                Flashcard currentCard = allFlashcards.get(cardIndex);

                questionText.setText(currentCard.getQuestion()); //Where is .getQuestion()?
                answerText.setText(currentCard.getAnswer());

                //Makes question initially visible upon button press:
                TextView questionText = findViewById(R.id.question_textView);
                TextView answerText = findViewById(R.id.answer_textView);
                questionText.setVisibility(View.INVISIBLE);
                answerText.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.button_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards == null || allFlashcards.size() == 0) {
                    return;
                }

                cardIndex -= 1;

                if (cardIndex >= allFlashcards.size()) {
                    Snackbar.make(view, "End of Cards!",Snackbar.LENGTH_SHORT).show();
                    cardIndex = 0;
                }

                Flashcard currentCard = allFlashcards.get(cardIndex);

                questionText.setText(currentCard.getQuestion()); //Where is .getQuestion()?
                answerText.setText(currentCard.getAnswer());

                //Makes question initially visible upon button press:
                TextView questionText = findViewById(R.id.question_textView);
                TextView answerText = findViewById(R.id.answer_textView);
                questionText.setVisibility(View.INVISIBLE);
                answerText.setVisibility(View.VISIBLE);
            }
        });




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!

            //TextView questionText = findViewById(R.id.question_textView);
           // TextView answerText = findViewById(R.id.answer_textView);

            //Strings
            String question = data.getExtras().getString("user_question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("user_answer");

            //TextViews
            ((TextView) findViewById(R.id.question_textView)).setText(question);
            ((TextView) findViewById(R.id.answer_textView)).setText(answer);

            //Saves flashcard to SQL database
           // flashcardDatabase.insertCard(new Flashcard(data.getExtras().getString("question"), data.getExtras().getString("answer")));

            flashcardDatabase.insertCard(new com.example.reviewly.Flashcard(question, answer));
            //Log commands
            Log.d("MainActivity", data.getExtras().getString("user_answer"));

            allFlashcards = flashcardDatabase.getAllCards();

            //Makes question initially visible upon reentry to activity:
            TextView questionText = findViewById(R.id.question_textView);
            TextView answerText = findViewById(R.id.answer_textView);
            questionText.setVisibility(View.INVISIBLE);
            answerText.setVisibility(View.VISIBLE);
            //Puts strings back into Textview
            //questionText.setText(string1);
            //answerText.setText(string2);
        }
    }


}