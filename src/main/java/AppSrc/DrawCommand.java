package AppSrc;

import javafx.geometry.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawCommand extends Command {
    private Context AppContext;

    ArrayList<Point2D> UpdatedPixels = new ArrayList<>();

    @Override
    public void Execute() {

        BufferedImage CurrentImage = AppContext.LoadedPhoto.DisplayedImage;

        BufferedImage NewImage = CurrentImage;

        ConvertPoints();

        int Red = (int) (AppContext.MarkerColor.getRed() * 255);
        int Green = (int) (AppContext.MarkerColor.getGreen() * 255);
        int Blue = (int) (AppContext.MarkerColor.getBlue() * 255);

        int rgb = (Red << 16) | (Green << 8) | Blue;

        for(Point2D Pixel : AppContext.MarkedPixels){

            int ImagePixelX = (int) (Pixel.getX() * (CurrentImage.getWidth()/AppContext.ImageWidth));
            int ImagePixelY = (int) (Pixel.getY() * (CurrentImage.getHeight()/AppContext.ImageHeight));

            NewImage.setRGB(ImagePixelX, ImagePixelY, rgb);

            if (ImagePixelX + 1 < NewImage.getWidth()) {
                NewImage.setRGB(ImagePixelX + 1, ImagePixelY, rgb);
            }
            if (ImagePixelX - 1 >= 0) {
                NewImage.setRGB(ImagePixelX - 1, ImagePixelY, rgb);
            }
            if (ImagePixelY + 1 < NewImage.getHeight()) {
                NewImage.setRGB(ImagePixelX, ImagePixelY + 1, rgb);
            }
            if (ImagePixelY - 1 >= 0) {
                NewImage.setRGB(ImagePixelX, ImagePixelY - 1, rgb);
            }
            if (ImagePixelX + 1 < NewImage.getWidth() && ImagePixelY + 1 < NewImage.getHeight()) {
                NewImage.setRGB(ImagePixelX + 1, ImagePixelY + 1, rgb);
            }
            if (ImagePixelX + 1 < NewImage.getWidth() && ImagePixelY - 1 >= 0) {
                NewImage.setRGB(ImagePixelX + 1, ImagePixelY - 1, rgb);
            }
            if (ImagePixelX - 1 >= 0 && ImagePixelY + 1 < NewImage.getHeight()) {
                NewImage.setRGB(ImagePixelX - 1, ImagePixelY + 1, rgb);
            }
            if (ImagePixelX - 1 >= 0 && ImagePixelY - 1 >= 0) {
                NewImage.setRGB(ImagePixelX - 1, ImagePixelY - 1, rgb);
            }
        }

        AppContext.LoadedPhoto.DisplayedImage = NewImage;
    }

    private void ConvertPoints(){

        for(Point2D Pixel : AppContext.MarkedPixels){

            Point2D UpdatedPixel = new Point2D(Pixel.getX() - AppContext.ImageTopLeft.getX(), Pixel.getY() - AppContext.ImageTopLeft.getY());

            // Add pixel if in image range
            if(UpdatedPixel.getX() <= AppContext.ImageWidth && UpdatedPixel.getX() >= 0){
                if(UpdatedPixel.getY() <= AppContext.ImageHeight && UpdatedPixel.getY() >= 0){
                    UpdatedPixels.add(UpdatedPixel);
                }
            }
        }
        AppContext.MarkedPixels = UpdatedPixels;
    }

    public DrawCommand(Context context) {
        this.AppContext = context;
    }
}
