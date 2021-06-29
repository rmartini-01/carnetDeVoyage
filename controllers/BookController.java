package controllers;

import models.Book;

public class BookController implements Observer{
    private Book book;
    public BookController(Book b){
        this.book = b;
        book.addObserver(this);
    }

    @Override
    public void update() {

    }
}
