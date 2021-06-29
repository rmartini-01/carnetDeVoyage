package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import models.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MenuController implements Observer{

    @FXML
    protected MenuBar menuController;
    @FXML
    protected MenuItem addPageItem;
    @FXML
    protected MenuItem deletePagesItem;
    protected MainController mainController;
    Book book;
    public MenuController(Book b, MainController main){
        this.book = b;
        mainController = main;
        book.addObserver(this);

    }
    /**
     * ferme l'application
     */
    public void exitApp(){
        Platform.exit();
        System.exit(0);
    }

    /**
     *ajoute une page normale
     * @param actionEvent événement
     */
    public void addPageItem(ActionEvent actionEvent) {
        book.addNormalPage();
    }


    /**
     * supprime toutes les pages du livre (sauf la page de garde)
     */
    public void deleteAllPages() {
        book.deleteAllPages();
        showScenes();
    }

    @Override
    public void update() {

    }

    /**
     * enregistrer un fichier
     * @param actionEvent
     */
    public void saveJson(ActionEvent actionEvent) {
        try {
            //dans le menu
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save book");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Json files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);

            //afficher la fenêtre pour enregistrer le fichier
            File file = fileChooser.showSaveDialog(null);
            book.saveAsJson(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * item du menu pour ouvrir un fichier
     * @throws FileNotFoundException le fichier n'existe pas
     */
    public void readJson() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Json", "*.json", "*.JSON"));
        File selectedFile = fileChooser.showOpenDialog(null);
        book.readJson(selectedFile);
    }

    /**
     * Vider la list des voyageurs
     */
    public void deleteAllTravelers() {
        book.getFrontPage().removeAllTravelers();
    }

    /**
     * item du menu pour réinitialiser le livre (en ouvrir un nouveau)
     */
    public void newBook() {
        book.copyBook(new Book("Travel book", "you"));
        showScenes();
    }
    /**
     * intervertir entre l'affichage de la page de couverture et les pages du livre.
     */
    public void showScenes(){
        if(book.getCurrentPage() != 0){
            //cacher la front page et afficher la normal page
            mainController.hideFrontPage();
        }else if(book.getCurrentPage()== 0) {
            //cacher la normal page et afficher la front page
            mainController.showFrontPage();
        }
    }
}
