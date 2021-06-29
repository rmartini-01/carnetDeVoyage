package exceptions;

import javafx.scene.control.Alert;

public class InvalidSelection extends Exception{
    public InvalidSelection(String message ){
            super(message);
    }

    /**
     * fenêtre modale d'alerte
     * @param e message d'erreur à afficher
     */
    public void alertWindow(String e){
        Alert alerte = new Alert(Alert.AlertType.WARNING);
        alerte.setTitle("Oops!");
        alerte.setHeaderText(null);
        alerte.setContentText(e);
        alerte.showAndWait();
    }
}
