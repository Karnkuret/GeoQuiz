package com.example.ricardo.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    //Tag for our log messages
    private static final String TAG = "QuizActivity";
    //Key for key-value pair stored in bundle
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEAT = "cheater";
    //Code for identifying CheatActivity
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;

    //Question class objects in an array, the answer to the question is added as requested by our constructor in Question.java
    private List<Question> mQuestionBank = new ArrayList<Question>() {
        {
            add(new Question(R.string.question_oceans, true, false));
            add(new Question(R.string.question_mideast, false, false));
            add(new Question(R.string.question_africa, false, false));
            add(new Question(R.string.question_americas, true, false));
            add(new Question(R.string.question_asia, true, false));
        }
    };

    //Counter for the number of questions
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    //Method updates question using objects from Question.java and the counter mCurrentIndex
    private void updateQuestion() {
        int question = mQuestionBank.get(mCurrentIndex).getTextResId();
        mQuestionTextView.setText(question);
    }

    //Answer is checked through our isAnswerTrue method in the Question.java class
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank.get(mCurrentIndex).isAnswerTrue();
        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgement_toast;
            mQuestionBank.get(mCurrentIndex).setCheater(true);
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        //Making the question appear in our activity_quiz.xml
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        //Toasts for the true button
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        //Toasts for the false button
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        //Function for previous button
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.size();
                if(mCurrentIndex < 0){
                    mCurrentIndex = mCurrentIndex + mQuestionBank.size();
                }
                mIsCheater = mQuestionBank.get(mCurrentIndex).isCheater();
                updateQuestion();
            }
        });
        //Function for next button
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
                mIsCheater = mQuestionBank.get(mCurrentIndex).isCheater();
                updateQuestion();
            }
        });
        //Function for CHEAT button which launches CheatActivity
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank.get(mCurrentIndex).isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        /*Checking bundle for the value of savedInstanceState,
         if it exists it is assigned to mCurrentIndex so that
         the app is not restarted when the layout changes to landscape*/
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT, false);
        }
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEAT, mIsCheater);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
