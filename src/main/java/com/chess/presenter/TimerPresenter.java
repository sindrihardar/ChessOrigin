package com.chess.presenter;

import com.chess.model.util.Colors;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class TimerPresenter {
    private boolean gameOver;
    private long initialDurationMillis, passedDurationMillis, startTimeMillis;
    private Colors color;
    private Timer timer;
    private ChessBoardPresenter gamePresenter;

    public TimerPresenter(long durationMillis, Colors color, ChessBoardPresenter gamePresenter) {
        this.initialDurationMillis = durationMillis;
        passedDurationMillis = 0;
        this.color = color;
        timer = null;
        this.gamePresenter = gamePresenter;
        gameOver = false;
    }

    /*
     * Starts the current timer, calculates passed duration.
     */
    public void start() {
        if (gameOver)
            return;
        startTimeMillis = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> gamePresenter.timerRanOutForColor(color));
                stop();
            }
        }, initialDurationMillis - passedDurationMillis);
    }

    /*
     * Terminates the current timer, setting the passed duration.
     */
    public void stop() {
        if (timer == null)
            return;
        timer.cancel();
        passedDurationMillis += System.currentTimeMillis() - startTimeMillis;
        startTimeMillis = 0;
        timer = null;
    }

    public void gameIsOver() {
        gameOver = true;
        stop();
    }

    public long getTimeRemaining() {
        if (timer == null) {
            return initialDurationMillis - passedDurationMillis;
        } else {
            return initialDurationMillis - (passedDurationMillis + System.currentTimeMillis() - startTimeMillis);
        }
    }
}
