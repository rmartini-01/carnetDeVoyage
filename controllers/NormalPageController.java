package controllers;

import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import exceptions.InvalidSelection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import models.Book;
import models.NormalPage;
import java.io.File;
import java.util.List;
import java.util.Optional;
import com.sothawo.mapjfx.*;

public class NormalPageController extends PageController {

    @FXML
    public Label tripDescriptionLabel;
    @FXML
    public Button deletePhotoButton;
    @FXML
    public AnchorPane normalPageAnchorPane;
    @FXML
    public Label latitudeLabel;
    @FXML
    public Label longitudeLabel;
    @FXML
    public ListView<String> imageListView;
    @FXML
    public Button addImage;
    @FXML
    public Label dateLabel;
    @FXML
    public ImageView imagePreview;
    @FXML
    private Label pageNumber;
    @FXML
    private Label title;
    @FXML
    private MapView mapView;

    /**
     * Constructeur
     * @param b carnet
     */
    public NormalPageController(Book b){
        super(b);
        mapView = new MapView();
        mapView.setAnimationDuration(500);
        book.addObserver(this);
    }
    /**
     * Handler pour ajouter une zone de texte
     */
    public void addTextHandler() {
        NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Describe your trip !");

        dialog.setHeaderText(null);
        dialog.getDialogPane().setMinHeight(200.0);
        dialog.getDialogPane().setMinWidth(500.0);
        dialog.setResizable(true);
        dialog.getDialogPane().setStyle("-fx-background-color: #E4D8A5;");

        VBox box = new VBox();
        TextArea area = new TextArea();
        area.setPromptText("...");
        area.setPrefWidth(100.0);
        area.setPrefHeight(300.0);
        area.setText(currentPage.getTripDescription());
        area.setWrapText(true);
        area.setFont(new Font("Gill Sans", 18.0));
        area.setStyle("-fx-border-width: 2px; -fx-border-color: #85BDEC; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        ButtonType saveButton = new ButtonType ("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);
        box.getChildren().addAll(area);
        dialog.getDialogPane().setContent(box);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            currentPage.setTripDescription(area.getText());
            update();
        }

    }

    /**
     * remets le texte par défaut
     */
    public void clearTextHandler() {
        NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
        currentPage.setTripDescription("Write about your trip!");
        update();
    }

    /**
     * handler du bouton qui ajoute une image à la listView
     * ajoute le path de l'image à la collection du modèle
     */
    public void handleAddImage(ActionEvent actionEvent) {
        NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setTitle("Choose images");
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        //si elle est différente on la change
        for(File f : selectedFiles){
            currentPage.addImagePath(f.getAbsolutePath());
        }

        update();

    }



    /**
     * affiche l'imageView et gère le style des cellules
     */
    public void addPathToList(){
        NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
        imageListView.getStylesheets().add("/css/ImageListStyle.css");
        imageListView.getItems().clear();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(currentPage.getPaths());
        imageListView.setItems(observableList);
        imageListView.setOrientation(Orientation.HORIZONTAL);
        imageListView.setCellFactory(param-> new ListCell<String>(){
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                if(empty){
                    setText(null);
                    setGraphic(null);
                }else{
                    Image image = new Image(new File(name).toURI().toString(), 300, 200, true, true);
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
                if(isSelected()){
                    setStyle("-fx-border-radius:10px; -fx-background-radius:10px; -fx-background-color: #B1ECE0;");
                }else{
                    setStyle("-fx-border-radius:10px; -fx-background-radius:10px; -fx-background-color: #EAEAEA;");
                }

            }
        });
    }
    /**
     * handler pour supprimer la première photo
     */
    public void deletePhoto() {
        NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
        String itemToDelete = imageListView.getSelectionModel().getSelectedItem();
        try{
            if (itemToDelete != null) {
                currentPage.removeImagePath(itemToDelete);
            }else{
                throw new InvalidSelection("An image has to be selected.");
            }
        }catch (InvalidSelection e){
            e.alertWindow("Please select an image to delete.");
        }
        currentPage.setPreviewImage("/resources/preview.png");
        update();
    }

    /**
     * context menu de l'image
     */
    public void imagePreviewContextMenu(){
        ContextMenu menu = new ContextMenu();

        MenuItem zoomIn = new MenuItem("Zoom in");
        MenuItem zoomOut = new MenuItem("Zoom out");

        zoomIn.setOnAction(e->{
            imagePreview.setScaleX(imagePreview.getScaleX()*1.2);
            imagePreview.setScaleY(imagePreview.getScaleY()*1.2);
        });
        zoomOut.setOnAction(e->{
            imagePreview.setScaleX(imagePreview.getScaleX()*0.8);
            imagePreview.setScaleY(imagePreview.getScaleY()*0.8);
        });
        menu.getItems().addAll(zoomIn, zoomOut);
        imagePreview.setOnContextMenuRequested(e ->{
             menu.show(imagePreview, e.getScreenX(), e.getScreenY());
        });
    }

    @Override
    public void update() {
        if(book.getCurrentPage() != 0){
            //affichage des boutons
            Image add = new Image(getClass().getResourceAsStream("/resources/add.png"), 20, 20,true, true);
            addImage.setGraphic(new ImageView(add));
            Image delete = new Image(getClass().getResourceAsStream("/resources/delete.png"), 20, 20,true, true);
            deletePhotoButton.setGraphic(new ImageView(delete));

            NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
            imagePreviewContextMenu();
            pageNumber.setText(""+currentPage.getPageNumber());
            title.setText(""+currentPage.getPageTitle());
            tripDescriptionLabel.setText(currentPage.getTripDescription());
            //mise à jour de l'affichage des images
            addPathToList();
            if(currentPage.getPreviewImage().equals("/resources/preview.png")){
                imagePreview.setImage(new Image(getClass().getResourceAsStream("/resources/preview.png")));
            }else{
                imagePreview.setImage(new Image(new File(currentPage.getPreviewImage()).toURI().toString()));
            }
            //mise à jour de la date
            dateLabel.setText(""+currentPage.getDate().getDayOfMonth()+"-"+ currentPage.getDate().getMonth()+"-"+currentPage.getDate().getYear());
            //mise en place de la carte
            Marker marker  = Marker.createProvided(Marker.Provided.ORANGE).setRotation(0);
            initializeMap(currentPage.getCoordinate());
            addMarker(marker);
            removeMarker();
        }
    }

    /**
     * initialisation de la carte
     * @param coord coordonnées par défaut
     */
    public void initializeMap(Coordinate coord){
        mapView.setCenter(coord);
        mapView.setMapType(MapType.OSM);
        mapView.initialize(Configuration.builder().showZoomControls(true).build());
        latitudeLabel.setText(" Latitude = " + mapView.getCenter().getLatitude().toString());
        longitudeLabel.setText("Longitude = "+ mapView.getCenter().getLongitude().toString());
    }

    /**
     * Handler lorsqu'on clique sur la carte pour ajouter un markeur
     * @param marker marker à ajouter
     */
    public void addMarker(Marker marker){
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            marker.setPosition(event.getCoordinate())
                    .setVisible(true);
            event.consume();
            if (marker.getVisible()) {
                mapView.addMarker(marker);
                latitudeLabel.setText("Latitude = " + marker.getPosition().getLatitude().toString());
                longitudeLabel.setText("Longitude = "+ marker.getPosition().getLongitude().toString());
            }
            NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
            currentPage.setCoordinate(marker.getPosition());
        });
    }

    /**
     * Handler pour effacer un marker si on appuie dessus.
     */
    public void removeMarker(){
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            Marker marker = event.getMarker();
            event.consume();
            marker.setVisible(false);
        });
    }

    /**
     * Gérer l'acceptation du drag and drop (il faut avoir des fichiers)
     * @param dragEvent événement
     */
     public void dragOver(DragEvent dragEvent) {
        if( dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasFiles()){
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();
    }

    /**
     * Gérer le dépôt des fichiers
     * @param dragEvent événement
     */
    public void handleDrop(DragEvent dragEvent) {
         List<File> files = dragEvent.getDragboard().getFiles();
        for (File file : files) {
            NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
            currentPage.addImagePath(file.getAbsolutePath());
        }
        addPathToList();
    }

    /**
     * sélectionner une nouvelle date
     */
    public void selectDate(){
        final Dialog<DatePicker> dialog = new Dialog<>();
        dialog.setTitle("Date");
        dialog.setHeaderText( "Choisir la date de la page");
        //icon calendrier
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        ButtonType saveButton = new ButtonType("Set Date", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        DatePicker datePicker = new DatePicker();
        dialog.getDialogPane().setContent(datePicker);
        datePicker.setTooltip(new Tooltip("Choisir la date de ce jour"));
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                if(book.getCurrentPage() != 0) {
                    NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
                    currentPage.setDate(datePicker.getValue());
                }
                }
            return null;
        });
        dialog.showAndWait();

    }

    /**
     * handler
     * @param click clique de la souris
     */
    public void selectItem(MouseEvent click) {
        NormalPage currentPage = (NormalPage) book.getPage(book.getCurrentPage());
        String selectedImage = imageListView.getSelectionModel().getSelectedItem();
        if(selectedImage!=null && click.getClickCount()==2){
            //afficher la photo
            currentPage.setPreviewImage(selectedImage);
            update();
        }


    }
}
