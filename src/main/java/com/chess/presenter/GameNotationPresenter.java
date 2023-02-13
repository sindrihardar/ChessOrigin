package com.chess.presenter;

import com.chess.view.Observer;

import java.util.LinkedList;
import java.util.List;

public class GameNotationPresenter implements Observable {
    private List<String> movementNotations;
    private List<Observer> observers;

    public GameNotationPresenter() {
        movementNotations = new LinkedList<>();
        observers = new LinkedList<>();
    }

    public void addToMovementNotations(String movementNotation) {
        movementNotations.add(movementNotation);
        notifyObservers();
    }

    public int getSizeOfMovementNotations() {
        return movementNotations.size();
    }

    public String getLastMovement() {
        return movementNotations.get(movementNotations.size() - 1);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update();
    }
}
