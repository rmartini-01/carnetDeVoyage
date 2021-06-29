package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Book;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class FrontPageController extends PageController implements Initializable {
    @FXML
    public Label bookTitle;
    @FXML
    public AnchorPane frontPageAnchorPane;
    @FXML
    public Label authorLabel;
    @FXML
    public Button addTravelerButton;
    @FXML
    private ListView<String> travelersList;
    @FXML
    private TextField travelerNameField;

    /**
     * Constructeur
     * @param b livre auquel appartient la page
     */
    public FrontPageController(Book b){
        super(b);
        book.addObserver(this);
    }

    /**
     * ajoute un voyageur à la liste
     */
    @FXML
    void handleAddTraveler() {
        if(!travelerNameField.getText().equals("")){
            book.getFrontPage().addTraveler(travelerNameField.getText());
        }
        travelerNameField.clear();
        //ajout des noms à la liste
        addTravelersToList();
    }

    /**
     * ajoute les voyageurs à la ListView
     */
    void addTravelersToList(){
        travelersList.getItems().clear();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(book.getFrontPage().getTravelers());
        travelersList.setItems(observableList);
        //style des cellules
        travelersList.setCellFactory(param -> new NameCellStyle(book));
    }
    /**
     * mise à jour de l'affichage
     */
    @Override
    public void update() {
        bookTitle.setText(book.getBookTitle());
        authorLabel.setText(book.getAuthor());
        addTravelersToList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContextMenu menu = new ContextMenu();
        MenuItem editBookTitle = new MenuItem("Edit book title");
        editBookTitle.setOnAction(e-> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit book title.");
            dialog.setHeaderText("Choose a new title for your book !");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                book.setBookTitle(dialog.getEditor().getText());
            }
        });
        menu.getItems().add(editBookTitle);
        bookTitle.setContextMenu(menu);
        menu.setId("context-menu");

    }

}
