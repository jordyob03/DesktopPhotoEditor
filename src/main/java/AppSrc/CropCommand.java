package AppSrc;

import javafx.geometry.Point2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class CropCommand extends Command {
    private Context AppContext;
    private Point2D CropTopLeftScreen;
    private double CropWidth;
    private double CropHeight;
    BufferedImage PreviousImage;
    BufferedImage PreviousImageOriginal; // Previous image with no lighting changes

    @Override
    public void Execute() {

        BufferedImage CurrentImage = AppContext.LoadedPhoto.DisplayedImage;
        BufferedImage CurrentOriginalImage = AppContext.LoadedPhoto.OriginalImage;

        PreviousImage = new BufferedImage(CurrentImage.getWidth(), CurrentImage.getHeight(), CurrentImage.getType());
        PreviousImage.getGraphics().drawImage(CurrentImage, 0, 0, null);

        PreviousImageOriginal = new BufferedImage(AppContext.LoadedPhoto.OriginalImage.getWidth(), AppContext.LoadedPhoto.OriginalImage.getHeight(), AppContext.LoadedPhoto.OriginalImage.getType());
        PreviousImageOriginal.getGraphics().drawImage(AppContext.LoadedPhoto.OriginalImage, 0, 0, null);

        SetParams();

        // Convert from screen coordinated to image pixel coordinates
        int ImageWidth = (int) (CropWidth * (CurrentImage.getWidth()/AppContext.ImageWidth));
        int ImageHeight = (int) (CropHeight * (CurrentImage.getHeight()/AppContext.ImageHeight));

        int ImageX = (int) (CropTopLeftScreen.getX() * (CurrentImage.getWidth()/AppContext.ImageWidth));
        int ImageY = (int) (CropTopLeftScreen.getY() * (CurrentImage.getHeight()/AppContext.ImageHeight));

        // Get subsection of image
        if(ImageWidth != 0 && ImageHeight != 0){

            BufferedImage newImage = CurrentImage.getSubimage(ImageX, ImageY, ImageWidth, ImageHeight);
            BufferedImage newImageOriginal = CurrentOriginalImage.getSubimage(ImageX, ImageY, ImageWidth, ImageHeight);

            // Update displayed image
            AppContext.LoadedPhoto.DisplayedImage = newImage;
            AppContext.LoadedPhoto.OriginalImage = newImageOriginal;
        }
    }
    @Override
    public void SetParams(){
        // Get crop points
        double cropMinX = Math.min(AppContext.CropPoint1.getX(), AppContext.CropPoint2.getX());
        double cropMaxX = Math.max(AppContext.CropPoint1.getX(), AppContext.CropPoint2.getX());
        double cropMinY = Math.min(AppContext.CropPoint1.getY(), AppContext.CropPoint2.getY());
        double cropMaxY = Math.max(AppContext.CropPoint1.getY(), AppContext.CropPoint2.getY());

        // get image points
        double imageMinX = AppContext.ImageTopLeft.getX();
        double imageMaxX = AppContext.ImageTopLeft.getX() + AppContext.ImageWidth;
        double imageMinY = AppContext.ImageTopLeft.getY();
        double imageMaxY = AppContext.ImageTopLeft.getY() + AppContext.ImageHeight;

        // Calculate the coordinates of the corners of the overlapping area
        double overlapMinX = Math.max(cropMinX, imageMinX);
        double overlapMaxX = Math.min(cropMaxX, imageMaxX);
        double overlapMinY = Math.max(cropMinY, imageMinY);
        double overlapMaxY = Math.min(cropMaxY, imageMaxY);

        // Calculate the width and height of the overlapping area
        double overlapWidth = Math.max(0, overlapMaxX - overlapMinX);
        double overlapHeight = Math.max(0, overlapMaxY - overlapMinY);

        // Get coordinates and measurements relative to top left corner of image
        CropTopLeftScreen = new Point2D(overlapMinX - AppContext.ImageTopLeft.getX(), overlapMinY - AppContext.ImageTopLeft.getY());

        CropWidth = overlapWidth;
        CropHeight = overlapHeight;
    }

    @Override
    public void Undo() {
        AppContext.LoadedPhoto.DisplayedImage = this.PreviousImage;
        AppContext.LoadedPhoto.OriginalImage = this.PreviousImageOriginal;
    }

    public CropCommand(Context context) {
        this.AppContext = context;
    }
}
