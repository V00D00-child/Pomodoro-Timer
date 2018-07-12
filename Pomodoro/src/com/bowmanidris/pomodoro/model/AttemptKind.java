package com.bowmanidris.pomodoro.model;

public enum AttemptKind {

    //Focus time is 25 mins. and break time is 5 mins.
    FOCUS(25 *60,"Focus Time"),
    BREAK(5 *60, "Break Time");

    private int mTotalSeconds;
    private String mDisplayName;

    AttemptKind(int mTotalSeconds, String displayName) {
        this.mTotalSeconds = mTotalSeconds;
        this.mDisplayName = displayName;
    }

    public int getTotalSeconds() {
        return mTotalSeconds;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}//end enum AttemptKind
