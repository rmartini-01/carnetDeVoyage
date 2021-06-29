import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import controllers.*;
import models.Book;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Book book = new Book("Travel book", "you");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/MainView.fxml"));
        MainController mainController = new MainController(book);
        BookController bookController = new BookController(book);
        MenuController menuController = new MenuController(book, mainController);
        NavigationController navController = new NavigationController(book, mainController);
        FrontPageController frontPageController = new FrontPageController(book);
        NormalPageController normalPageController = new NormalPageController(book);

        loader.setControllerFactory(ic->{
            if(ic.equals(controllers.MainController.class)) return mainController;
            else if (ic.equals(controllers.BookController.class)) return bookController;
            else if(ic.equals(controllers.MenuController.class)) return menuController;
            else if(ic.equals(controllers.FrontPageController.class)) return frontPageController;
            else if(ic.equals(controllers.NormalPageController.class)) return normalPageController;
            else if (ic.equals(controllers.NavigationController.class)) return navController;
            else return null;
        });

        Parent root = loader.load();
        primaryStage.setTitle("Travel book");
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("/css/MenuStyle.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();

    }


}
