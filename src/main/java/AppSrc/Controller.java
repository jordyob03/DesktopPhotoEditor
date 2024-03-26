package AppSrc;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Context AppContext =  Context.getInstance();
    private Rectangle HighlightingRect;
    @FXML
    private ImageView imageView;

    // Lighting controls
    @FXML
    private Slider brightnessSlider;
    @FXML
    private Slider contrastSlider;
    @FXML
    private Slider exposureSlider;

    // Cropping
    @FXML
    private ToggleButton CropButton;

    // Drawing
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Listeners for lighting sliders
        brightnessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AppContext.BrightnessPercent = newValue.intValue();
            BrightnessCommand newCommand = new BrightnessCommand(AppContext);
            newCommand.Execute();
            AppContext.UIHandler.updateDisplayedImage(AppContext);
        });
        contrastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AppContext.ContrastPercent = newValue.intValue();
            ContrastCommand newCommand = new ContrastCommand(AppContext);
            newCommand.Execute();
            AppContext.UIHandler.updateDisplayedImage(AppContext);
        });
        exposureSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AppContext.ExposurePercent = newValue.intValue();
            ExposureCommand newCommand = new ExposureCommand(AppContext);
            newCommand.Execute();
            AppContext.UIHandler.updateDisplayedImage(AppContext);
        });

        // Update cropping rectangle
        AppContext.UIHandler.CreateCroppingRectangle();
        HighlightingRect = AppContext.UIHandler.CroppingRectangle;

        // Set global image view
        AppContext.imageView = imageView;
    }

    @FXML
    private void openFile() {

        AppContext.LoadedPhoto = new Photo();
        AppContext.LoadedPhoto.openFile();
        AppContext.UIHandler.updateDisplayedImage(AppContext);
    }
    @FXML
    private void SaveAsFile(){
      AppContext.LoadedPhoto.SaveAsFile();
    }
    @FXML
    private void SaveFile(){
       AppContext.LoadedPhoto.SaveFile();
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
        AppContext.CommandStack.push(newCommand);
        AppContext.UIHandler.updateDisplayedImage(AppContext);
    }

    @FXML
    private void Crop(){

        DeselectButtons(CropButton);

        if(CropButton.isSelected() && AppContext.ImageLoaded){

            AppContext.AppScene.setOnMousePressed(event -> {
                AppContext.EventHandler.HandleMouseClickCrop(event, AppContext);
            });
            AppContext.AppScene.setOnMouseDragged(event -> {
                AppContext.EventHandler.HandleMouseDraggedCrop(event, AppContext);
            });
            AppContext.AppScene.setOnMouseReleased(event -> {
                AppContext.EventHandler.HandleMouseReleasedCrop(event, AppContext);
                CropButton.setSelected(false);
                // Remove cropping rectangle
                Pane rootPane = (Pane) AppContext.AppScene.getRoot();
                rootPane.getChildren().remove(HighlightingRect);
            });

            //Add cropping rectangle
            Pane rootPane = (Pane) AppContext.AppScene.getRoot();
            rootPane.getChildren().add(HighlightingRect);
        }
        else{
            AppContext.AppScene.setOnMousePressed(null);
            AppContext.AppScene.setOnMouseDragged(null);
            AppContext.AppScene.setOnMouseReleased(null);
        }
    }

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
            AppContext.AppScene.setOnMouseDragged(event -> {
                AppContext.EventHandler.HandleMouseDraggedDraw(event, AppContext);
            });
            AppContext.AppScene.setOnMouseReleased(event -> {
                AppContext.EventHandler.HandleMouseReleasedDraw(event, AppContext);
            });
        }
    }

    @FXML
    private void Undo(){

        if(!AppContext.CommandStack.isEmpty()){
            Command LastCommand = AppContext.CommandStack.pop();
            LastCommand.Undo();
            AppContext.UIHandler.updateDisplayedImage(AppContext);
        }
    }

    public void DeselectButtons(ToggleButton CurrentButton){
        ToggleButton[] ToggleButtons = {RedMarker, OrangeMarker, YellowMarker, GreenMarker, BlueMarker, PurpleMarker, PinkMarker, WhiteMarker, BlackMarker, CropButton};
        for (ToggleButton markerButton : ToggleButtons) {
            if (markerButton != CurrentButton) {
                markerButton.setSelected(false);
            }
        }
    }

}
