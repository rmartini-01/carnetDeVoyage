package models;


public abstract class Page {
    public void setBook(Book book) {
        this.book = book;
    }

    protected transient Book book;
    protected String pageTitle;
    protected int pageNumber;
    protected String bookTitle;

    public Page(Book b){
        this.book = b;
        bookTitle= b.getBookTitle();
        pageTitle = "";
    }

    /**
     * getter
     * @return numéro de la page
     */
    public int getPageNumber() {
        return pageNumber;
    }
    /**
     * Getter
     * @return titre de la page
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * setter
     * @param pageTitle nouveau titre de la page
     */
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * Set nouveau titre
     * @param bookTitle nouveau titre
     */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }



}
