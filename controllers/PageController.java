package controllers;

import models.Book;

public class PageController implements Observer{
    protected Book book;
    PageController(Book b){
        book = b;
    }
    @Override
    public void update() {

    }
}
