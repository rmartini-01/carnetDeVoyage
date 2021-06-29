package controllers;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.Book;

public class NameCellStyle extends ListCell<String> {
    private Book book ;
    private final AnchorPane pane = new AnchorPane();
    private final Label label = new Label("");
    private final Button remove = new Button("");

    /**
     * constructeur
     * @param book le carnet
     */
    public NameCellStyle(Book book){
        super();
        this.book = book;
        AnchorPane.setTopAnchor(label, 8.0);
        AnchorPane.setLeftAnchor(label , 10.0);
        AnchorPane.setRightAnchor(remove, 10.0);
        pane.getChildren().addAll(label, remove);
        remove.setOnAction(e->{
            //si la personne qu'on supprime était déclarée en tant qu'auteure du livre, on remet "you" par défaut.
            if(getItem().equals(book.getAuthor())){
                book.setAuthor("you");
            }
            book.getFrontPage().removeTraveler(getItem());
        });
    }
    /**
     * mise à jour de l'affichage de la cellule
     * @param name nom
     * @param empty bool
     */
        public void updateItem(String name, boolean empty){
        super.updateItem(name, empty);
            if(isSelected()){
                setStyle("-fx-border-radius:10px; -fx-background-radius:10px; -fx-background-color: #B1ECE0;");
            }else{
                setStyle("-fx-border-radius:10px; -fx-background-radius:10px; -fx-background-color: #FFFFFF;");
            }
            if(empty){
                setText(null);
                setGraphic(null);
            }else {
                if (name != null) {
                    //style du text
                    label.setText(name);
                    label.setFont(Font.font("Gill Sans", 14));
                    label.setTextFill(Color.BLACK);
                    //style du bouton
                    remove.setStyle("-fx-text-fill: Black;");
                    remove.setTooltip(new Tooltip("Remove traveler."));
                    Image image = new Image(getClass().getResourceAsStream("/resources/delete.png"), 20, 20, true, false);
                    remove.setGraphic(new ImageView(image));
                    remove.setStyle("-fx-background-color:transparent; -fx-cursor:HAND");
                    setGraphic(pane);
                    ContextMenu menu = new ContextMenu();
                    MenuItem isTheAuthor = new MenuItem("Set as author");
                    isTheAuthor.setOnAction(e -> book.setAuthor(label.getText()));
                    menu.getItems().add(isTheAuthor);
                    setContextMenu(menu);
                }
            }
    }
}
