package controllers;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import exceptions.InvalidSelection;
import javafx.util.Duration;
import models.Book;
import models.Page;
import java.net.URL;
import java.util.ResourceBundle;


public class NavigationController implements Observer , Initializable {

    private final Book book;
    @FXML
    public ImageView infoImage;
    @FXML
    public Label infoLabel;
    @FXML
    private ListView<String> pagesLV;
    @FXML
    private TextField pageNameTextFieldController;
    private final MainController mainController;
    public NavigationController(Book b, MainController main) {
        this.book = b;
        mainController = main;
        book.addObserver(this);
    }

    /**
     * ajoute une page normale
     */
    @FXML
    void handleAddPage() {
        book.addNormalPage();
    }

    /**
     * supprime une page de la liste
     */
    @FXML
    void handleDeletePage(){
        String p = pagesLV.getSelectionModel().getSelectedItem();
        if(p!=null){
            int pageNumber = book.getPageFromTitle(p).getPageNumber();
            if(pageNumber!=0){
                if(pageNumber == book.getCurrentPage()){
                    //si on supprime la page actuelle, on affiche celle d'avant dans la liste.
                    book.setCurrentPage(pagesLV.getItems().indexOf(p)-1);
                }
                book.deletePage(pageNumber);
            }else{
                try {
                    throw new InvalidSelection("The cover page cannot be deleted.");
                } catch (InvalidSelection invalidSelection) {
                    invalidSelection.alertWindow(invalidSelection.getMessage());
                }
            }
        }else{
            try {
                throw new InvalidSelection("Please select a page to delete it. \n Note: the cover page cannot be deleted.");
            } catch (InvalidSelection invalidSelection) {
                invalidSelection.alertWindow(invalidSelection.getMessage());
            }
        }

    }

    /**
     * ajoute une page à la liste de navigation
     */
    void addPageToList() {
        pagesLV.getItems().clear();
        book.deleteObsPages();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add(book.getFrontPage().getPageTitle());
        for (Page p : book) {
            observableList.add(p.getPageTitle());
            book.addObservablePage(p.getPageTitle());
        }
        pagesLV.setItems(observableList);
        //style des cellules
        pagesLV.setCellFactory(param -> new PageCellStyle(book, mainController));
    }

    /**
     * handler du bouton "update", mets à jour le titre de la page.
     */
    public void updatePageTitle(){
        String newName = pageNameTextFieldController.getText();
        String selectedPageName = pagesLV.getSelectionModel().getSelectedItem();
        Page selectedPage = book.getPageFromTitle(selectedPageName);
        if (newName != null && selectedPageName != null) {
            book.editPageName(selectedPage.getPageTitle(), newName);
            pageNameTextFieldController.clear();
        }else{
            try {
                throw new InvalidSelection("Please select a page to rename it.");
            } catch (InvalidSelection invalidSelection) {
                invalidSelection.alertWindow(invalidSelection.getMessage());
            }
        }
    }


    /**
     * affiche le titre de la page sélectionnée dans la zone de texte
     */
    public void selectedItem(MouseEvent click) {
        String selectedPage = pagesLV.getSelectionModel().getSelectedItem();
        if (click.getClickCount() == 2) {
            pageNameTextFieldController.clear();
            book.setCurrentPage(book.getPageFromTitle(selectedPage).getPageNumber());
            showScenes();
        }else{
            //si on sélectionne un élément on peut le modifier
            pageNameTextFieldController.setText(selectedPage);
        }
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

    /**
     * mise à jour de l'affichage
     */
    @Override
    public void update() {
        //ajoute la page à la liste
        addPageToList();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPageToList();
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(2));
        transition.setNode(infoImage);
        transition.setToY(-15);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }

    /**
     * met en marche l'animation de l'encadré d'information
     */
    public void showInfo() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setNode(infoLabel);
        transition.setFromX(-200);
        transition.setToX(0);
        transition.setAutoReverse(true);
        transition.setCycleCount(1);
        transition.play();
        infoLabel.setVisible(!infoLabel.isVisible());
    }
}
