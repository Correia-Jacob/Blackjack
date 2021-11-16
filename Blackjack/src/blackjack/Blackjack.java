package blackjack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Blackjack extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("JavaFX Blackjack");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Blackjack.class.getResourceAsStream("icon.png")));
        stage.setResizable(false);
        stage.show();
            
    }
    
}
