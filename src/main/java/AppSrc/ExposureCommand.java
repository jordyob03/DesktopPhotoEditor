package AppSrc;

import java.awt.image.BufferedImage;

public class ExposureCommand extends Command {
    private Context AppContext;
    @Override
    public void Execute(){
        double ExposureFactor = (AppContext.ExposurePercent/100.0) - 1;
        BufferedImage originalImage = AppContext.LoadedPhoto.OriginalImage;
        BufferedImage newImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {

                int RGB = originalImage.getRGB(x, y);

                // Extract
                int Alpha = (RGB >> 24) & 0xFF;
                int Red = (RGB >> 16) & 0xFF;
                int Green = (RGB >> 8) & 0xFF;
                int Blue = RGB & 0xFF;

                Red = (int) (Red * Math.pow(2,ExposureFactor));
                Green = (int) (Green * Math.pow(2,ExposureFactor));
                Blue = (int) (Blue * Math.pow(2,ExposureFactor));


                // Ensure that the values are within 0-255
                Red = Math.min(Math.max(Red, 0), 255);
                Green = Math.min(Math.max(Green, 0), 255);
                Blue = Math.min(Math.max(Blue, 0), 255);

                // Recombine
                int AdjustedRGB = (Alpha << 24) | (Red << 16) | (Green << 8) | Blue;
                newImage.setRGB(x, y, AdjustedRGB);
            }
        }
        AppContext.LoadedPhoto.DisplayedImage = newImage;
    }
    public  ExposureCommand(Context context){
        this.AppContext = context;
    }
    }

