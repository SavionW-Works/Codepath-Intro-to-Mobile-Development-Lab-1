package com.example.reviewly;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Intent;
import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.security.AccessController;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //Database for flashcards
    FlashcardDatabase flashcardDatabase;

    //List for all flashcards
    List<Flashcard> allFlashcards;

    int cardIndex = 0;

    //private AccessController v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        //Check if database is empty before displaying

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        TextView questionText = findViewById(R.id.question_textView);
        TextView answerText = findViewById(R.id.answer_textView);

        questionText.setVisibility(View.INVISIBLE); //Makes question visible and answer invisible for some reason :/
        answerText.setVisibility(View.VISIBLE);

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.question_textView)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.answer_textView)).setText(allFlashcards.get(0).getAnswer());
        }

        ImageView addButton = findViewById(R.id.button_Add);

        String data = getIntent().getStringExtra("some_key2");


        //Question Switch
        questionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Circle animation start
                View answerSideView = findViewById(R.id.answer_textView);

                // get the center for the clipping circle
                int cx = answerText.getWidth() / 2;
                int cy = answerText.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                questionText.setVisibility(View.INVISIBLE);
                answerText.setVisibility(View.VISIBLE);

                anim.setDuration(250);
                anim.start();
                //Circle animation end
            }

        });

        //Answer Switch
        answerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Circle animation start
                View questionSideView = findViewById(R.id.question_textView);

                // get the center for the clipping circle
                int cx = answerText.getWidth() / 2;
                int cy = answerText.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(questionSideView, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                questionText.setVisibility(View.VISIBLE);
                answerText.setVisibility(View.INVISIBLE);

                anim.setDuration(250);
                anim.start();
                //Circle animation end
            }

        });

        //Moves AddCardActivity
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
                //Animation:
                overridePendingTransition(R.anim.right_in, R.anim.left_out);





            }
        });

        //Next Button
        findViewById(R.id.button_Next).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                //Animation-----------------------------------
                //Loads animation resources
                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);



                findViewById(R.id.answer_textView).startAnimation(leftOutAnim);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        findViewById(R.id.answer_textView).startAnimation(leftOutAnim);
                        answerText.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        findViewById(R.id.answer_textView).startAnimation(rightInAnim);
                        answerText.setVisibility(View.VISIBLE);
                        //Index stuff----------------------------------------------------
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





                        //End of index stuff---------------------------------
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Unused
                    }


                });
                //End of Animation-------------------------









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
                //questionText.setVisibility(View.VISIBLE);
                //answerText.setVisibility(View.INVISIBLE);

                //Loads animation resources
                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                findViewById(R.id.question_textView).startAnimation(leftOutAnim);
                questionText.setVisibility(View.VISIBLE);
                answerText.setVisibility(View.INVISIBLE);
                rightInAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        findViewById(R.id.question_textView).startAnimation(rightInAnim);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        findViewById(R.id.question_textView).startAnimation(leftOutAnim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Unused
                    }
                });
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