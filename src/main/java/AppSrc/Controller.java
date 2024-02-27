package AppSrc;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private Stage primaryStage;

    @FXML
    private ImageView imageView;


    @FXML
    private Slider brightnessSlider;

    @FXML
    private Slider contrastSlider;

    @FXML
    private Slider exposureSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add change listeners to sliders
        brightnessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

            AppContext.BrightnessPercent = newValue.intValue();
            LightingCommand newCommand = new LightingCommand(AppContext);
            newCommand.Execute();
            updateDisplayedImage();
        });

        contrastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        });

        exposureSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        });
    }

    private Context AppContext =  Context.getInstance();


    // Todo: Make file browser only show .jpg and .png files
    // Method to handle opening a file
    @FXML
    private void openFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if(selectedFile != null){
            AppContext.LoadedPhoto = new Photo(selectedFile);
            updateDisplayedImage();
        }
    }

    private void updateDisplayedImage(){
        try {
            // Get and display image
            Image fxImage = AppContext.LoadedPhoto.toFXImage(AppContext.LoadedPhoto.DisplayedImage);
            //Image fxImage = (Image) AppContext.LoadedPhoto.DisplayedImage;
            imageView.setImage(fxImage);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the file.", Alert.AlertType.ERROR);
        }

    }

    // Method to display an alert
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
