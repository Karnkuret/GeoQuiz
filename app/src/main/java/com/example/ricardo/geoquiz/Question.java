package com.example.ricardo.geoquiz;

/**
 * Created by Ricardo on 10/21/2016.
 */
public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsCheater;

    public boolean isCheater() {
        return mIsCheater;
    }

    public void setCheater(boolean cheater) {
        mIsCheater = cheater;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(int textResId, boolean answerTrue, boolean cheaterTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mIsCheater = cheaterTrue;
    }
}
