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
import javax.imageio.ImageIO;

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
            BrightnessCommand newCommand = new BrightnessCommand(AppContext);
            newCommand.Execute();
            updateDisplayedImage();
        });

        contrastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AppContext.ContrastPercent = newValue.intValue();
            ContrastCommand newCommand = new ContrastCommand(AppContext);
            newCommand.Execute();
            updateDisplayedImage();
        });

        exposureSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AppContext.ExposurePercent = newValue.intValue();
            ExposureCommand newCommand = new ExposureCommand(AppContext);
            newCommand.Execute();
            updateDisplayedImage();
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
            AppContext.OpenedFilePath = selectedFile.getAbsolutePath();
            updateDisplayedImage();
        }
    }
    // Method to handle choosing a directory and saving the image there
    @FXML
    private void SaveAsFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        FileChooser.ExtensionFilter pngExtFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        FileChooser.ExtensionFilter jpgExtFilter = new FileChooser.ExtensionFilter("JPEG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(pngExtFilter);
        fileChooser.getExtensionFilters().add(jpgExtFilter);
        File selectedFile = fileChooser.showSaveDialog(primaryStage);

        // Store new file path
        AppContext.SaveFilePath = selectedFile.getAbsolutePath();

        if (selectedFile != null) {
            try {
                ImageIO.write(AppContext.LoadedPhoto.DisplayedImage, "png", selectedFile);

                // Store new file path
                AppContext.SaveFilePath = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to save the file.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void SaveFile(){

        if(AppContext.SaveFilePath == null){
            SaveAsFile();
            return;
        }
        else{
            File file = new File(AppContext.SaveFilePath);
            try{
                ImageIO.write(AppContext.LoadedPhoto.DisplayedImage, "png", file);
            }
            catch(Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to save the file.", Alert.AlertType.ERROR);
            }
        }
    }

    private void updateDisplayedImage(){
        try {
            // Get and display image
            Image fxImage = AppContext.LoadedPhoto.toFXImage(AppContext.LoadedPhoto.DisplayedImage);
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
