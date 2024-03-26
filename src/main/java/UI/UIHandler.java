package UI;

import AppSrc.Context;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UIHandler {

    public Rectangle CroppingRectangle;

    public void updateDisplayedImage(Context AppContext){
        try {
            Image fxImage = AppContext.LoadedPhoto.toFXImage(AppContext.LoadedPhoto.DisplayedImage);
            AppContext.imageView.setImage(fxImage);
            AppContext.ImageLoaded = true;

        }
        catch (Exception e) {

        }
    }

    public void UpdateRectangle(double Width, double Height, double X, double Y){

        CroppingRectangle.setX(X);
        CroppingRectangle.setY(Y);
        CroppingRectangle.setWidth(Width);
        CroppingRectangle.setHeight(Height);
    }

    public void CreateCroppingRectangle(){

        CroppingRectangle = new Rectangle();
        CroppingRectangle .setStroke(Color.WHITE);
        CroppingRectangle .setStrokeWidth(1);
        CroppingRectangle .setFill(Color.rgb(255, 255, 255, 0.1));
    }
}
