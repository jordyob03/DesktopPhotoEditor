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

    public BufferedImage DisplayedImage;

    public BufferedImage DisplayedImageNoLighting;

    public BufferedImage DisplayedImageWithFilter;

    public BufferedImage OriginalImage;

    public Photo(File PhotoFile){

    try {
        OriginalImage = ImageIO.read(PhotoFile);
        DisplayedImage = OriginalImage;
        DisplayedImageWithFilter = OriginalImage;
        DisplayedImageNoLighting = OriginalImage;
    }
    catch(IOException e) {

    }
    }

    public Image toFXImage(BufferedImage bufferedImage) throws IOException{

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        return new javafx.scene.image.Image(is);
    }
}
