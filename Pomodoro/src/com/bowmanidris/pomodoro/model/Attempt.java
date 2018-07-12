package com.bowmanidris.pomodoro.model;

public class Attempt {

    private String mMessage;
    private int mRemainingSeconds;
    private AttemptKind mKind;

    public Attempt(String message, AttemptKind kind) {
        this.mMessage = message;
        this.mKind = kind;
        mRemainingSeconds = kind.getTotalSeconds();
    }

    public String getMessage() {
        return mMessage;
    }

    public int getRemainingSeconds() {
        return mRemainingSeconds;
    }

    public AttemptKind getKind() {
        return mKind;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public void tick() {
        mRemainingSeconds --;

    }

    @Override
    public String toString() {
        return "Attempt{" +
                "mMessage='" + mMessage + '\'' +
                ", mRemainingSeconds=" + mRemainingSeconds +
                ", mKind=" + mKind +
                '}';
    }

    public void save() {
        //if we had a data base this is where we would save
        System.out.printf("Saving: %s %n", this);

    }

}//end class Attempt
