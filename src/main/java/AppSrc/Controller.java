package AppSrc;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Controller {

    private Stage primaryStage;

    @FXML
    private ImageView imageView;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }


    // Method to handle opening a file
    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);


        context.LoadedPhoto = new Photo(selectedFile);

        if (context.LoadedPhoto.bufferedImage != null) {
            try {
                // Duplicate the selected photo
                System.out.println(context.LoadedPhoto.bufferedImage);
                Image fxImage = context.LoadedPhoto.toFXImage(context.LoadedPhoto.bufferedImage);

                // Display the image in the ImageView
                imageView.setImage(fxImage);

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to open the file.", Alert.AlertType.ERROR);
            }
        }
    }

    private String getFileExtension(String filePath) {
        int lastIndex = filePath.lastIndexOf('.');
        if (lastIndex == -1) {
            return ""; // No extension found
        }
        return filePath.substring(lastIndex + 1);
    }

    private void saveImageToFile(String sourceFilePath, String destinationFilePath) throws IOException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
            outputStream = new FileOutputStream(destinationFilePath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }


    // Method to display an alert dialog
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
