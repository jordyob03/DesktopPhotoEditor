package AppSrc;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class FilterCommand extends Command {

    private Context AppContext;

    BufferedImage PreviousImage;

    int RedFactor;
    int GreenFactor;
    int BlueFactor;

    public void Execute(){

        BufferedImage originalImage = AppContext.LoadedPhoto.OriginalImage;
        BufferedImage newImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        PreviousImage = new BufferedImage(AppContext.LoadedPhoto.DisplayedImage.getWidth(), AppContext.LoadedPhoto.DisplayedImage.getHeight(), AppContext.LoadedPhoto.DisplayedImage.getType());
        PreviousImage.getGraphics().drawImage(AppContext.LoadedPhoto.DisplayedImage, 0, 0, null);

        // Handle color filters
        if(AppContext.CurrentFilter != Filters.BLACKWHITE){

            SetFactors();
            for (int x = 0; x < originalImage.getWidth(); x++) {
                for (int y = 0; y < originalImage.getHeight(); y++) {

                    int RGB = originalImage.getRGB(x, y);

                    // Extract
                    int Alpha = (RGB >> 24) & 0xFF;
                    int Red = (RGB >> 16) & 0xFF;
                    int Green = (RGB >> 8) & 0xFF;
                    int Blue = RGB & 0xFF;

                    Red = (int) Red + RedFactor;
                    Green = (int) Green + GreenFactor;
                    Blue = (int) Blue + BlueFactor;

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

        if(AppContext.CurrentFilter == Filters.BLACKWHITE){

            for (int x = 0; x < originalImage.getWidth(); x++) {
                for (int y = 0; y < originalImage.getHeight(); y++) {

                    int RGB = originalImage.getRGB(x, y);

                    // Extract
                    int Alpha = (RGB >> 24) & 0xFF;
                    int Red = (RGB >> 16) & 0xFF;
                    int Green = (RGB >> 8) & 0xFF;
                    int Blue = RGB & 0xFF;

                    int averageIntensity = (Red + Green + Blue)/3;

                    Red = averageIntensity;
                    Green = averageIntensity;
                    Blue = averageIntensity;

                    // Recombine
                    int AdjustedRGB = (Alpha << 24) | (Red << 16) | (Green << 8) | Blue;
                    newImage.setRGB(x, y, AdjustedRGB);
                }
            }
            AppContext.LoadedPhoto.DisplayedImage = newImage;
        }
    }
    // Constructor
    public FilterCommand(Context context) {
        this.AppContext = context;
    }

    // Set RGB factors according to color of filter
    private void SetFactors() {

        Filters filter = AppContext.CurrentFilter;

        switch (filter) {
            case RED:
                RedFactor = 50;
                GreenFactor = 0;
                BlueFactor = 0;
                break;
            case ORANGE:
                RedFactor = 70;
                GreenFactor = 25;
                BlueFactor = 0;
                break;
            case YELLOW:
                RedFactor = 70;
                GreenFactor = 50;
                BlueFactor = 0;
                break;
            case GREEN:
                RedFactor = 0;
                GreenFactor = 70;
                BlueFactor = 0;
                break;
            case BLUE:
                RedFactor = 0;
                GreenFactor = 0;
                BlueFactor = 70;
                break;
            case PURPLE:
                RedFactor = 50;
                GreenFactor = 0;
                BlueFactor = 70;
                break;
            case PINK:
                RedFactor = 100;
                GreenFactor = 25;
                BlueFactor = 50;
                break;
        }
    }

    @Override
    public void Undo() {
        AppContext.LoadedPhoto.DisplayedImage = this.PreviousImage;
    }
}
