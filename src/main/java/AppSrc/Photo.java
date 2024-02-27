package AppSrc;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class Photo {

    File selectedFile;
    private String OriginFilePath;

    public BufferedImage DisplayedImage;

    public BufferedImage OriginalImage;

    public Photo(File PhotoFile){

        OriginFilePath = PhotoFile.getAbsolutePath();

    try {
        OriginalImage = ImageIO.read(PhotoFile);
        DisplayedImage = OriginalImage;
    }
    catch(IOException e) {
    }
    }

    public Image toFXImage(BufferedImage bufferedImage) throws IOException{

        //Image fxImage = SwingFxUtils.toFxImage(bufferedImage, null);


       ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        return new javafx.scene.image.Image(is);
    }

    public void AdjustBrightness(int BrightnessPercent) {

        double BrightnessFactor = BrightnessPercent / 100.0;

        DisplayedImage = new BufferedImage(OriginalImage.getWidth(), OriginalImage.getHeight(), OriginalImage.getType());

        for (int x = 0; x < OriginalImage.getWidth(); x++) {
            for (int y = 0; y < OriginalImage.getHeight(); y++) {

                int RGB = OriginalImage.getRGB(x, y);

                // Extract
                int Alpha = (RGB >> 24) & 0xFF;
                int Red = (RGB >> 16) & 0xFF;
                int Green = (RGB >> 8) & 0xFF;
                int Blue = RGB & 0xFF;

                Red = (int) (Red * BrightnessFactor);
                Green = (int) (Green * BrightnessFactor);
                Blue = (int) (Blue * BrightnessFactor);

                // Ensure that the values are within 0-255
                Red = Math.min(Math.max(Red, 0), 255);
                Green = Math.min(Math.max(Green, 0), 255);
                Blue = Math.min(Math.max(Blue, 0), 255);

                // Recombine
                int AdjustedRGB = (Alpha << 24) | (Red << 16) | (Green << 8) | Blue;
                DisplayedImage.setRGB(x, y, AdjustedRGB);
            }
        }
    }
}
