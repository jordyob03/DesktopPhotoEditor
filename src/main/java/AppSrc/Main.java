package AppSrc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Context AppContext = Context.getInstance();
        Controller AppController = new Controller();

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StartingScreen.fxml"));

        Parent root = loader.load();
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
