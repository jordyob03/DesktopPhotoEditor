package AppSrc;

import Objects.Point2D;

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

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private Context AppContext =  Context.getInstance();

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

        HighlightingRect = new Rectangle();
        HighlightingRect.setStroke(Color.WHITE);
        HighlightingRect.setStrokeWidth(2);
        HighlightingRect.setFill(Color.rgb(255, 255, 255, 0.1));
    }


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

    @FXML
    private void SetRed(){
        AppContext.CurrentFilter = Filters.RED;
        ApplyFilter();
    }
    @FXML
    private void SetOrange(){
        AppContext.CurrentFilter = Filters.ORANGE;
        ApplyFilter();
    }
    @FXML
    private void SetYellow(){
        AppContext.CurrentFilter = Filters.YELLOW;
        ApplyFilter();
    }
    @FXML
    private void SetGreen(){
        AppContext.CurrentFilter = Filters.GREEN;
        ApplyFilter();
    }
    @FXML
    private void SetBlue(){
        AppContext.CurrentFilter = Filters.BLUE;
        ApplyFilter();
    }
    @FXML
    private void SetPurple(){
        AppContext.CurrentFilter = Filters.PURPLE;
        ApplyFilter();
    }
    @FXML
    private void SetPink(){
        AppContext.CurrentFilter = Filters.PINK;
        ApplyFilter();
    }
    @FXML
    private void SetBW(){
        AppContext.CurrentFilter = Filters.BLACKWHITE;
        ApplyFilter();
    }
    private void ApplyFilter(){

        FilterCommand newCommand = new FilterCommand(AppContext);
        newCommand.Execute();
        updateDisplayedImage();

    }

    // Store two coordinates for cropping area
    private Point2D StartingPixel;
    private Point2D EndPixel;
    private Rectangle HighlightingRect;
    @FXML
    private void Crop(){

        AppContext.AppScene.setOnMousePressed(this::HandleMousePressedCropping);
        AppContext.AppScene.setOnMouseDragged(this::HandleMouseDraggedCropping);
        AppContext.AppScene.setOnMouseReleased(this::HandleMouseReleasedCropping);

        Pane rootPane = (Pane) AppContext.AppScene.getRoot();
        rootPane.getChildren().add(HighlightingRect);

    }

    private void HandleMousePressedCropping(MouseEvent event) {

        StartingPixel.X = (int) event.getSceneX();
        StartingPixel.Y = (int) event.getSceneY();

        HighlightingRect.setX(StartingPixel.X);
        HighlightingRect.setY(StartingPixel.Y );
        HighlightingRect.setWidth(0);
        HighlightingRect.setHeight(0);
    }

    private void HandleMouseDraggedCropping(MouseEvent event) {

        Point2D CurrentPixel = new Point2D();

        CurrentPixel.X = (int) event.getSceneX();
        CurrentPixel.Y = (int) event.getSceneX();

        double Width = CurrentPixel.X - StartingPixel.X ;
        double Height = CurrentPixel.Y  - StartingPixel.Y;

        HighlightingRect.setWidth(Math.abs(Width));
        HighlightingRect.setHeight(Math.abs(Height));

        HighlightingRect.setX(Width > 0 ? StartingPixel.X : CurrentPixel.X);
        HighlightingRect.setY(Height > 0 ? StartingPixel.Y : CurrentPixel.Y);
    }

    private void HandleMouseReleasedCropping(MouseEvent event) {

        EndPixel.X = (int) event.getSceneX();
        EndPixel.Y = (int) event.getSceneY();

        // Remove event handlers and rectangle from the pane
        Pane rootPane = (Pane) AppContext.AppScene.getRoot();
        rootPane.getChildren().remove(HighlightingRect);
        AppContext.AppScene.setOnMousePressed(null);
        AppContext.AppScene.setOnMouseDragged(null);
        AppContext.AppScene.setOnMouseReleased(null);

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
