package AppSrc;

import java.awt.image.BufferedImage;

public class LightingCommand extends Command {

    int BrightnessPercent;
    int ContrastPercent;
    int ExposurePercent;

    Context AppContext;

    // TODO: Implement contrast and exposure changes
    @Override
    public void Execute(){

        double brightnessFactor = AppContext.BrightnessPercent / 100.0;

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

                Red = (int) (Red * brightnessFactor);
                Green = (int) (Green * brightnessFactor);
                Blue = (int) (Blue * brightnessFactor);

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

    public  LightingCommand(Context context){
        this.AppContext = context;
    }





}
