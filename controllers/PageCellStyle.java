package controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.Book;

import java.util.Optional;


public class PageCellStyle extends ListCell<String> {
    private final HBox box = new HBox();
    private final Label label = new Label("");
    private final Book book;
    private final MainController main;
    /**
     * Constructeur
     */
    public PageCellStyle(Book book, MainController mainController){
        super();
        this.book = book;
        main = mainController;
        box.getChildren().add(label);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(0, 5, 0, 5));
    }

    /**
     * mise à jour de l'affichage de la cellule
     * @param name nom
     * @param empty bool
     */
    public void updateItem(String name, boolean empty){
        super.updateItem(name, empty);
        if(isSelected()){
            setStyle("-fx-background-color:#B1ECE0;");
        }else{
            setStyle("-fx-background-color:#FAFAEB;");
        }
        if(empty){
            setText(null);
            setGraphic(null);
        }else {
            if(name!=null) {
                //style du text
                label.setText(name);
                label.setFont(Font.font("Gill Sans", 14));
                label.setTextFill(Color.BLACK);
                //ajout de la box à la cellule
                setGraphic(box);

                ContextMenu menu = new ContextMenu();

                MenuItem renamePage = new MenuItem("Rename page");
                MenuItem deletePage = new MenuItem("Delete page");

                menu.setId("modifierPage");
                getStyleClass().add("modifierPage");

                renamePage.setOnAction(e ->{
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Edit page Title");
                    dialog.setHeaderText("New name : ");
                    dialog.setGraphic(null);
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(p -> {
                        book.editPageName(getItem(), dialog.getResult());

                    });

                });

                deletePage.setOnAction(e-> {
                    book.deletePage(book.getPageFromTitle(getItem()).getPageNumber());
                    showScenes();
                });

                menu.getItems().addAll(renamePage, deletePage);
                setContextMenu(menu);
            }
        }
    }

    /**
     * intervertir entre l'affichage de la page de couverture et les pages du livre.
     */
    public void showScenes(){
        if(book.getCurrentPage() != 0){
            //cacher la front page et afficher la normal page
            main.hideFrontPage();
        }else if(book.getCurrentPage()== 0) {
            //cacher la normal page et afficher la front page
            main.showFrontPage();
        }
    }
}
