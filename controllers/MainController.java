package controllers;

import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import models.Book;

public class MainController implements Observer {
    public MenuBar menuController;
    public BorderPane bookController;
    public AnchorPane frontPageController;
    public AnchorPane navigationController;
    public AnchorPane normalPageController;
    public AnchorPane mainScene;
    private Book book;

    public MainController(Book b){
        this.book = b;
        book.addObserver(this);
    }

    /**
     * Cacher la page de garde et afficher la page sélectionnée du livre.
     */
    public void hideFrontPage(){
        frontPageController.setVisible(false);
        normalPageController.setVisible(true);
    }
    /**
     * afficher la page de garde et cacher le reste des pages du livre.
     */
    public void showFrontPage(){
        frontPageController.setVisible(true);
        normalPageController.setVisible(false);
    }
    @Override
    public void update() {

    }
}

