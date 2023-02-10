package com.chess.presenter;

import com.chess.model.util.Colors;

public class GameMediator implements Mediator {
    private BoardPresenter boardPresenter;
    private TimerPresenter currentTimerPresenter, whiteTimerPresenter, blackTimerPresenter;

    public GameMediator() {
        this.boardPresenter = new LocalBoardPresenter(this);
        this.whiteTimerPresenter = new TimerPresenter(10 * 60 * 1000, Colors.WHITE, this);
        this.blackTimerPresenter = new TimerPresenter(10 * 60 * 1000, Colors.BLACK, this);
        currentTimerPresenter = whiteTimerPresenter;
    }

    public BoardPresenter getBoardPresenter() {
        return boardPresenter;
    }

    public TimerPresenter getWhiteTimerPresenter() {
        return whiteTimerPresenter;
    }

    public TimerPresenter getBlackTimerPresenter() {
        return blackTimerPresenter;
    }

    @Override
    public void notify(Object o) {
        if (o instanceof BoardPresenter) {
            BoardPresenter boardPresenter = (BoardPresenter) o;
            if (boardPresenter.isPlayerInCheckmate() || boardPresenter.isGameInStalemate()) {
                whiteTimerPresenter.gameIsOver();
                blackTimerPresenter.gameIsOver();
            } else if (boardPresenter.getCurrentPlayersColor() == Colors.WHITE && currentTimerPresenter != whiteTimerPresenter) {
                currentTimerPresenter = whiteTimerPresenter;
            } else if (boardPresenter.getCurrentPlayersColor() == Colors.BLACK && currentTimerPresenter != blackTimerPresenter) {
                currentTimerPresenter = blackTimerPresenter;
            }
        } else if (o instanceof TimerPresenter) {
            TimerPresenter timerPresenter = (TimerPresenter) o;
            if (timerPresenter.isGameOver())
                boardPresenter.timerRanOutForColor(timerPresenter.getColor());
        } else {
            throw new IllegalArgumentException("Mediator notifier isn't a valid sender.");
        }
    }
}
