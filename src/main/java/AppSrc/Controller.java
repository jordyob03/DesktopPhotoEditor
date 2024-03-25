package AppSrc;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javax.imageio.ImageIO;
import javafx.scene.control.ToggleButton;

import javafx.geometry.Point2D;

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

    @FXML
    private ToggleButton CropButton;

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
        HighlightingRect.setStrokeWidth(1);
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

    private Rectangle HighlightingRect;
    @FXML
    private void Crop(){

        if(CropButton.isSelected() && AppContext.ImageLoaded){

            AppContext.AppScene.setOnMousePressed(this::HandleMousePressedCropping);
            AppContext.AppScene.setOnMouseDragged(this::HandleMouseDraggedCropping);
            AppContext.AppScene.setOnMouseReleased(this::HandleMouseReleasedCropping);

            Pane rootPane = (Pane) AppContext.AppScene.getRoot();
            rootPane.getChildren().add(HighlightingRect);
        }
        else{
            AppContext.AppScene.setOnMousePressed(null);
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);

            Pane rootPane = (Pane) AppContext.AppScene.getRoot();
            rootPane.getChildren().remove(HighlightingRect);
        }
    }

    private void HandleMousePressedCropping(MouseEvent event) {

        // Set first crop point
        AppContext.CropPoint1 = new Point2D(event.getSceneX(), event.getSceneY());

        // Get image coordinates and dimensions relative to screen
        AppContext.ImageTopLeft = imageView.localToScreen(0, 0);

        Point2D ImageViewBottomRightScreen = imageView.localToScreen(imageView.getBoundsInLocal().getWidth(), imageView.getBoundsInLocal().getHeight());
        AppContext.ImageWidth = ImageViewBottomRightScreen.getX() - AppContext.ImageTopLeft.getX();
        AppContext.ImageHeight = ImageViewBottomRightScreen.getY() - AppContext.ImageTopLeft.getY();

        HighlightingRect.setX(AppContext.CropPoint1.getX());
        HighlightingRect.setY(AppContext.CropPoint1.getY());
        HighlightingRect.setWidth(0);
        HighlightingRect.setHeight(0);
    }

    private void HandleMouseDraggedCropping(MouseEvent event) {

        // Current position of the mouse
        Point2D CurrentPixel = new Point2D(event.getSceneX(), event.getSceneY());

        // Update rectangle
        double Width = CurrentPixel.getX() - AppContext.CropPoint1.getX() ;
        double Height = CurrentPixel.getY()  - AppContext.CropPoint1.getY();

        HighlightingRect.setWidth(Math.abs(Width));
        HighlightingRect.setHeight(Math.abs(Height));

        HighlightingRect.setX(Width > 0 ? AppContext.CropPoint1.getX() : CurrentPixel.getX());
        HighlightingRect.setY(Height > 0 ? AppContext.CropPoint1.getY() : CurrentPixel.getY());
    }

    private void HandleMouseReleasedCropping(MouseEvent event) {

        // Get second cropping point
        AppContext.CropPoint2 = new Point2D(event.getSceneX(), event.getSceneY());

        // Remove event handlers and rectangle
        Pane rootPane = (Pane) AppContext.AppScene.getRoot();
        rootPane.getChildren().remove(HighlightingRect);
        AppContext.AppScene.setOnMousePressed(null);
        AppContext.AppScene.setOnMouseDragged(null);
        AppContext.AppScene.setOnMouseReleased(null);

        // Create cropping command
        CropCommand newCommand = new CropCommand(AppContext);
        newCommand.Execute();
        updateDisplayedImage();

        HighlightingRect.setWidth(0);
        HighlightingRect.setHeight(0);
        CropButton.setSelected(false);
    }

    // Marker buttons
    @FXML
    private ToggleButton RedMarker;
    @FXML
    private ToggleButton OrangeMarker;
    @FXML
    private ToggleButton YellowMarker;
    @FXML
    private ToggleButton GreenMarker;
    @FXML
    private ToggleButton BlueMarker;
    @FXML
    private ToggleButton PurpleMarker;
    @FXML
    private ToggleButton PinkMarker;
    @FXML
    private ToggleButton WhiteMarker;
    @FXML
    private ToggleButton BlackMarker;


    @FXML
    private void DrawRed() {
        if(RedMarker.isSelected()){
            DeselectButtons(RedMarker);
            Draw(Color.RED);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }
    @FXML
    private void DrawOrange() {
        if(OrangeMarker.isSelected()){
            DeselectButtons(OrangeMarker);
            Draw(Color.ORANGE);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

    @FXML
    private void DrawYellow() {
        if(YellowMarker.isSelected()){
            DeselectButtons(YellowMarker);
            Draw(Color.YELLOW);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

    @FXML
    private void DrawGreen() {
        if(GreenMarker.isSelected()){
            DeselectButtons(GreenMarker);
            Draw(Color.GREEN);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

    @FXML
    private void DrawBlue() {
        if(BlueMarker.isSelected()){
            DeselectButtons(BlueMarker);
            Draw(Color.BLUE);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

    @FXML
    private void DrawPurple() {
        if(PurpleMarker.isSelected()){
            DeselectButtons(PurpleMarker);
            Draw(Color.PURPLE);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

    @FXML
    private void DrawPink() {
        if(PinkMarker.isSelected()){
            DeselectButtons(PinkMarker);
            Draw(Color.PINK);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

    @FXML
    private void DrawBlack() {
        if(BlackMarker.isSelected()){
            DeselectButtons(BlackMarker);
            Draw(Color.BLACK);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

    @FXML
    private void DrawWhite() {
        if(WhiteMarker.isSelected()){
            DeselectButtons(WhiteMarker);
            Draw(Color.WHITE);
        }
        else{
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

    private void Draw(Color MarkerColor){

        if(AppContext.ImageLoaded){
            AppContext.MarkerColor = MarkerColor;
            AppContext.AppScene.setOnMouseDragged(this::HandleMouseDraggedDrawing);
            AppContext.AppScene.setOnMouseReleased(this::HandleMouseReleasedDrawing);
        }
    }

    private void DeselectButtons(ToggleButton CurrentButton){
        ToggleButton[] markerButtons = {RedMarker, OrangeMarker, YellowMarker, GreenMarker, BlueMarker, PurpleMarker, PinkMarker, WhiteMarker, BlackMarker, CropButton};
        for (ToggleButton markerButton : markerButtons) {
            if (markerButton != CurrentButton) {
                markerButton.setSelected(false);
            }
        }
    }

    private void HandleMouseDraggedDrawing(MouseEvent event) {

        // Current position of the mouse
        Point2D CurrentPixel = new Point2D(event.getSceneX(), event.getSceneY());
        AppContext.MarkedPixels.add(CurrentPixel);

    }
    private void HandleMouseReleasedDrawing(MouseEvent event) {

        // Get second cropping point
        AppContext.CropPoint2 = new Point2D(event.getSceneX(), event.getSceneY());

        AppContext.ImageTopLeft = imageView.localToScreen(0, 0);

        Point2D ImageViewBottomRightScreen = imageView.localToScreen(imageView.getBoundsInLocal().getWidth(), imageView.getBoundsInLocal().getHeight());
        AppContext.ImageWidth = ImageViewBottomRightScreen.getX() - AppContext.ImageTopLeft.getX();
        AppContext.ImageHeight = ImageViewBottomRightScreen.getY() - AppContext.ImageTopLeft.getY();

        DrawCommand newCommand = new DrawCommand(AppContext);
        newCommand.Execute();
        updateDisplayedImage();


        AppContext.MarkedPixels.clear();
    }

    private void updateDisplayedImage(){
        try {
            Image fxImage = AppContext.LoadedPhoto.toFXImage(AppContext.LoadedPhoto.DisplayedImage);
            imageView.setImage(fxImage);
            AppContext.imageView = imageView;
            AppContext.ImageLoaded = true;

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the file.", Alert.AlertType.ERROR);
            AppContext.ImageLoaded = false;
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
