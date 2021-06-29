package models;

import java.util.ArrayList;

public class FrontPage extends Page{
    private final ArrayList<String> travelers= new ArrayList<>(3);
    public FrontPage(Book b) {
        super(b);
        pageTitle = "Cover page";
        pageNumber = 0;
    }

    /**
     * ajoute un voyageur à la liste
     * @param name nom du voyageur
     */
    public void addTraveler(String name){
        travelers.add(name);
        book.notifyObservers();
    }
    /**
     * supprime un voyageur de la liste
     * @param name nom du voyageur
     */
    public void removeTraveler(String name){
        this.travelers.remove(name);
        book.notifyObservers();

    }
    /**
     * supprime tous les voyageurs
     */
    public void removeAllTravelers(){
        this.travelers.clear();
        book.notifyObservers();
    }

    /**
     * getter
     * @return la liste des voyageurs
     */
    public ArrayList<String> getTravelers() {
        return travelers;
    }


}
