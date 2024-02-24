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

    public BufferedImage bufferedImage;

    public Photo(File PhotoFile){

        OriginFilePath = PhotoFile.getAbsolutePath();

    try {
        bufferedImage = ImageIO.read(PhotoFile);
    }
    catch(IOException e) {
    }

    for(int x = 0 ; x < bufferedImage.getWidth(); x++){
        for(int y = 0; y < bufferedImage.getHeight(); y++){

            double RGBValue = bufferedImage.getRGB(x,y);
            bufferedImage.setRGB(x,y, (int)RGBValue);
        }
    }
    }

    public Image toFXImage(BufferedImage bufferedImage) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        return new javafx.scene.image.Image(is);
    }

    private void AdjustBrightness(int BrightnessPercent){
    }

}
