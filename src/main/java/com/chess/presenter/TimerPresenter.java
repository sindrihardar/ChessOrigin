package com.chess.presenter;

import com.chess.model.util.Colors;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class TimerPresenter {
    private long initialDurationMillis, passedDurationMillis, startTimeMillis;
    private Colors color;
    private Timer timer;
    private ChessBoardPresenter gamePresenter;

    public TimerPresenter(long durationMillis, Colors color, ChessBoardPresenter gamePresenter) {
        this.initialDurationMillis = durationMillis;
        this.color = color;
        timer = new Timer();
        this.gamePresenter = gamePresenter;
    }

    /*
     * Starts the current timer, calculates passed duration.
     */
    public void start() {
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
        timer.cancel();
        passedDurationMillis += System.currentTimeMillis() - startTimeMillis;
        startTimeMillis = 0;
    }

    public long getTimeRemaining() {
        if (startTimeMillis == 0) {
            return initialDurationMillis - passedDurationMillis;
        } else {
            return initialDurationMillis - (passedDurationMillis + System.currentTimeMillis() - startTimeMillis);
        }
    }
}
