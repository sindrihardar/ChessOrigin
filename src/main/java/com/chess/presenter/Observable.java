package com.chess.presenter;

import com.chess.view.Observer;

public interface Observable {
    public void attach(Observer observer);
    public void detach(Observer observer);
    public void notifyObservers();
}
