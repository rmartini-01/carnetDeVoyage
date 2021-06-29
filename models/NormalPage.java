package models;

import com.sothawo.mapjfx.Coordinate;

import java.time.LocalDate;
import java.util.ArrayList;

public class NormalPage extends Page{
    private String tripDescription;
    private ArrayList<String> paths = new ArrayList<>(3);
    private LocalDate date;
    private String previewImage;
    private Coordinate coordinate;
    /**
     * Constructeur
     * @param b livre
     * @param num numéro de la page
     */
    public NormalPage(Book b, int num) {
        super(b);
        pageNumber = num;
        pageTitle = "Page : " + pageNumber;
        tripDescription = "Write about your trip!";
        coordinate = new Coordinate(48.693442, 6.183106);
        date = LocalDate.now();
        previewImage = "/resources/preview.png";
    }

    /**
     * getter
     * @return date de la page
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * setter
     * @param date nouvelle date de la page
     */
    public void setDate(LocalDate date) {
        this.date = date;
        book.notifyObservers();
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    /**
     * ajoute le path de l'image à la collection
     * @param s chemin vers l'image
     */
    public void addImagePath(String s){
        paths.add(s);
    }

    /**
     * supprime une image de la collection à travers son path
     * @param s chemin de l'image à supprimer
     */
    public void removeImagePath(String s){
        paths.remove(s);
    }

    /**
     * getter
     * @return la collection des chemins des images
     */
    public ArrayList<String> getPaths() {
        return paths;
    }
    /**
     * Getter de la description de texte
     * @return la description de texte
     */
    public String getTripDescription() {
        return tripDescription;
    }

    /**
     * setter
     * @param tripDescription modifier la description du voyage
     */
    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    /**
     * Getter
     * @return location coordinates
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Setter
     * @param coordinate sets the new location
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        System.out.println("new coordinates ");
    }

}
