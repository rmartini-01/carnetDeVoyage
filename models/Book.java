package models;

import com.google.gson.*;
import javafx.stage.FileChooser;
import tools.PageNumberGenerator;
import java.io.*;
import java.util.*;

public class Book extends Observed  implements Iterable<NormalPage> {
    private String bookTitle;
    private FrontPage fPage;
    private HashMap<Integer, NormalPage> pages = new HashMap<>();
    private ArrayList<String> observablePages; //
    private int currentPage; //
    private String author;


    /**
     * Constructor
     * @param title titre du livre
     * @param author auteur
     */
    public Book(String title, String author){
        this.bookTitle = title;
        this.author = author;
        observablePages = new ArrayList<>();
        fPage = new FrontPage(this);
        currentPage = 0;
    }

    /**
     * getter
     * @return titre du livre
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * setter
     * @param bookTitle nouveau titre
     */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        for(Page p : this){
            p.setBookTitle(bookTitle);
        }
        notifyObservers();
    }

    /**
     * getter
     * @return nom de l'auteur
     */
    public String getAuthor() {
        return author;
    }

    /**
     * setter
     * @param author nouveau auteur
     */
    public void setAuthor(String author) {
        this.author = author;
        notifyObservers();
    }
    /**
     * getter
     * @return page de garde du livre
     */
    public FrontPage getFrontPage() {
        return fPage;
    }

    /**
     * getter
     * @return les pages
     */
    public Page getPage(int i) {
        return pages.get(i);
    }

    /**
     * setter
     * @param observablePages observable pages
     */
    public void addObservablePage(String observablePages) {
        this.observablePages.add(observablePages);
    }

    /**
     * supprime la liste des pages observables
     */
    public void deleteObsPages(){
        observablePages.clear();
    }

    /**
     * getter
     * @param title titre de la page
     * @return la page
     */
    public Page getPageFromTitle(String title){
        if(fPage.getPageTitle().equals(title)){
            return fPage;
        }else{
            for(Page p : this){
                if(p.getPageTitle().equals(title)){
                    return p;
                }
            }
        }

        return null;
    }
    /**
     * ajoute une page au livre
     */
    public void addNormalPage(){
        int numPage =  PageNumberGenerator.getInstance().getNewPageNumber();
        this.pages.put(numPage, new NormalPage(this, numPage));
        notifyObservers();
    }

    /**
     * supprime une page du livre
     * @param p  page à supprimer
     */
    public void deletePage(int p){
        this.pages.remove(p); //on supprime la page
        setCurrentPage(fPage.getPageNumber());
        notifyObservers();  // on met à jour l'affichage
    }

    /**
     * met à jour le titre d'une page
     * @param pageName page à renommer
     * @param newName nouveau nom
     */
    public void editPageName(String pageName, String newName){
        getPageFromTitle(pageName).setPageTitle(newName);
        notifyObservers();
    }

    /**
     * supprime toutes les pages
     */
    public void deleteAllPages(){
        setCurrentPage(0);
        pages.clear();
        //on remet à jour la numérotation des pages
        PageNumberGenerator.getInstance().reset();
        notifyObservers();
    }

    /**
     * getter
     * @return retourne la page affichée
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * setter
     * @param currentPage met à jour la page à afficher
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        notifyObservers();
    }
    @Override
    public Iterator<NormalPage> iterator() {
        return pages.values().iterator();
    }

    /**
     * enregistrer le modèle en tant que fichier json
     * @throws IOException file writer
     */
   public void saveAsJson(File file) throws IOException {

       if (file != null) {
           FileWriter write = new FileWriter(file);
           GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
           builder.serializeNulls();
           Gson gson =builder.create();
           BufferedWriter bw = new BufferedWriter(write);

           bw.write(gson.toJson(this));
           bw.close();
       }
    }

    /**
     * fonction pour copier le livre et le charger
     * @param book livre à charger
     */
    public void copyBook(Book book){

        bookTitle = book.bookTitle;
        fPage = book.fPage;
        pages = book.pages;
        observablePages =book.observablePages;
        currentPage = 0;
        author = book.author;
        fPage.setBook(this);

        for(Page p : this){
            p.setBook(this);
        }
        PageNumberGenerator.getInstance().initParam(pages.size());
        //mise à jour de l'affichage
        notifyObservers();
    }

    /**
     * charger un fichier json
     * @param f le fichier à charger
     * @throws FileNotFoundException si le fichier n'existe pas
     */
    public void readJson(File f) throws FileNotFoundException {
        notifyObservers();
        FileReader read = new FileReader(f);
        BufferedReader br = new BufferedReader(read);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        StringBuilder buffer = new StringBuilder();
        try {

            String line;
            while((line=br.readLine())!=null){
                buffer.append(line).append("\n");
            }

            copyBook(gson.fromJson(buffer.toString(), Book.class));
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifyObservers();
    }



}
