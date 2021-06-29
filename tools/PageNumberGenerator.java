package tools;

public class PageNumberGenerator {
    private int pageNumber = 0;
    private static PageNumberGenerator instance = new PageNumberGenerator();

    /**
     * returns instance
     * @return class instance
     */
    public static PageNumberGenerator getInstance(){
        return instance;
    }

    /**
     * mettre le numéro de page à jour lorsqu'on charge un livre!
     * @param num nouveau num de page
     */
    public void initParam(int num){
        pageNumber = num;
    }
    /**
     * returns new page number
     * @return created page number
     */
    public int getNewPageNumber(){
        return ++pageNumber;//On convertit le numéro d'étape en chaîne de caractères
    }

    /**
     * @return returns current page number
     */
    public int getCurrentPageNumber(){
        return pageNumber;
    }

    /**
     * remettre les numérotation de pages à 0
     */
    public void reset(){
        pageNumber = 0;
    }
}
