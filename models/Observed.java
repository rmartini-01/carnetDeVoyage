package models;

import controllers.Observer;

import java.util.ArrayList;

public class Observed {
    private transient ArrayList<Observer> observers;

    /**
     * Constructor
     */
    public Observed(){
        observers = new ArrayList<>(6); //6 controllers for now
    }

    /**
     * Function to add a controller to the model
     * @param o observer to add
     */
    public void addObserver(Observer o){
        observers.add(o);
    }

    /**
     * notifies all observers of any change
     */
    public void notifyObservers(){
        for (Observer o : observers){
            o.update(); //On informe toutes les vues d'une modification
        }
    }

}
