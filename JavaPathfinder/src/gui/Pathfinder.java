package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Pathfinder extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("pathfinder.fxml"));
        stage.setTitle("Pathfinder");
        stage.setScene(new Scene(root, 300, 300));
        stage.show();
    }
    
    public static void main(String[] args) {launch(args);}
}
