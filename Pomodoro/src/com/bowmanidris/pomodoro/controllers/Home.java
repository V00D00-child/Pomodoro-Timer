package com.bowmanidris.pomodoro.controllers;

import com.bowmanidris.pomodoro.model.Attempt;
import com.bowmanidris.pomodoro.model.AttemptKind;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;


public class Home {
    private final AudioClip mApplause;

    @FXML
    private VBox container;

    @FXML
    private Label title;

    @FXML
    private TextArea message;

    private Attempt mCurrentAttempt;

    private StringProperty mTimerText;

    private Timeline mTimeLine;

    public Home(){
        mTimerText = new SimpleStringProperty();
        setTimerText(0);
        mApplause = new AudioClip(getClass().getResource("/sounds/applause.mp3").toExternalForm());
    }

    public String getTimerText() {
        return mTimerText.get();
    }

    public StringProperty timerTextProperty() {
        return mTimerText;
    }

    public void setTimerText(String timerText) {
        this.mTimerText.set(timerText);
    }

    public void setTimerText(int remainingSeconds){

        int minutes = remainingSeconds / 60;
        int seconds =  remainingSeconds % 60;
        setTimerText(String.format("%02d:%02d", minutes,seconds));//format the timer UI
    }

    //This method starts a new attempt
    private void prepareAttempt(AttemptKind kind){

        reset();//reset the screen

        mCurrentAttempt = new Attempt("",kind); //make the newly created attempt the current attempt
        addAttemptStyle(kind);//add style depending on the attempt type
        title.setText(kind.getDisplayName()); //display the title
        setTimerText(mCurrentAttempt.getRemainingSeconds()); //call the int method

        //set up the time Animation
        mTimeLine = new Timeline();
        //set up how many pages you will have
        mTimeLine.setCycleCount(kind.getTotalSeconds());
        mTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {

            //This what will run every KeyFrame
            mCurrentAttempt.tick();//this method call makes the timer decrement by 1 second
            setTimerText(mCurrentAttempt.getRemainingSeconds());
        } ));

        // after the timer has stopped switch the AttemptKind
        mTimeLine.setOnFinished(e -> {
            saveCurrentAttempt();
            mApplause.play();
            //if the current time is focus switch to break otherwise switch to focus
            prepareAttempt(mCurrentAttempt.getKind() ==  AttemptKind.FOCUS ?
                        AttemptKind.BREAK : AttemptKind.FOCUS);
        });

    }//end prepareAttempt()

    private void saveCurrentAttempt() {
        mCurrentAttempt.setMessage(message.getText());
        mCurrentAttempt.save();
    }

    private void reset() {
        clearAttemptStyles();   //clear ant styles
        if (mTimeLine != null && mTimeLine.getStatus() == Animation.Status.RUNNING){
            mTimeLine.stop();
        }//end if statement
    }

    public void playTimer(){
        container.getStyleClass().add("playing");
        mTimeLine.play();
    }

    public void pauseTimer(){
        container.getStyleClass().remove("playing");
        mTimeLine.pause();
    }

    //This method add styling depending on the attempt kind
    private void addAttemptStyle(AttemptKind kind) {
        container.getStyleClass().add(kind.toString().toLowerCase());
    }

   //This method clears all styling
    private void clearAttemptStyles(){
        container.getStyleClass().remove("playing");
        for(AttemptKind kind: AttemptKind.values()){
            container.getStyleClass().remove(kind.toString());
        }//end loop
    }

    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }

    public void handlePlay(ActionEvent actionEvent) {
        if (mCurrentAttempt == null){
            handleRestart(actionEvent);
        }else{
            playTimer();
        }
        playTimer();
    }

    public void handlePaused(ActionEvent actionEvent) {
        pauseTimer();
    }
}//end Home controller class
