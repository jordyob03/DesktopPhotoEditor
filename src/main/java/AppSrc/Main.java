package AppSrc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Context AppContext = new Context();
        Controller AppController = new Controller();

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StartingScreen.fxml"));

        Parent root = loader.load();

        AppController = loader.getController();
        AppController.setContext(AppContext);


        Scene scene = new Scene(root, 1000, 500);

        stage.setTitle("PhotoEditor");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
