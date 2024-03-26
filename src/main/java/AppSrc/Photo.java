package AppSrc;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class Photo {

    private Stage primaryStage;
    public BufferedImage DisplayedImage;
    public BufferedImage DisplayedImageNoLighting;
    public BufferedImage DisplayedImageWithFilter;
    public BufferedImage OriginalImage;

    public String OpenedFilePath;
    public String SaveFilePath;

    public Image toFXImage(BufferedImage bufferedImage) throws IOException{

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        return new javafx.scene.image.Image(is);
    }

    public void openFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if(selectedFile != null){

            try {
                // Set photo variables
                OpenedFilePath = selectedFile.getAbsolutePath();
                OriginalImage = ImageIO.read(selectedFile);
                DisplayedImage = OriginalImage;
                DisplayedImageWithFilter = OriginalImage;
                DisplayedImageNoLighting = OriginalImage;
            }
            catch (Exception e) {

            }
        }
    }

    public void SaveAsFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        FileChooser.ExtensionFilter pngExtFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        FileChooser.ExtensionFilter jpgExtFilter = new FileChooser.ExtensionFilter("JPEG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(pngExtFilter);
        fileChooser.getExtensionFilters().add(jpgExtFilter);
        File selectedFile = fileChooser.showSaveDialog(primaryStage);

        // Store new file path
        SaveFilePath = selectedFile.getAbsolutePath();

        if (selectedFile != null) {
            try {
                ImageIO.write(DisplayedImage, "png", selectedFile);
                SaveFilePath = selectedFile.getAbsolutePath();
            }
            catch (Exception e) {
            }
        }
    }

    public void SaveFile(){

        if(SaveFilePath == null){
            SaveAsFile();
            return;
        }
        else{
            File file = new File(SaveFilePath);
            try{
                ImageIO.write(DisplayedImage, "png", file);
            }
            catch(Exception e) {

            }
        }
    }
}
