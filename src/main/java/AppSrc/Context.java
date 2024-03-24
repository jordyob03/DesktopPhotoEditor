package AppSrc;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


// Singleton class for current application context
public class Context {

    private static Context instance;

    Scene AppScene;

    public boolean ImageLoaded;
    public ImageView imageView;

    AnchorPane root;

    public Point2D ImageTopLeft;
    public double ImageWidth = 0.0;
    public double ImageHeight = 0.0;


    public int BrightnessPercent = 100;

    public double ContrastPercent = 100.0;

    public double ExposurePercent = 100.0;

    Filters CurrentFilter;

    public Photo LoadedPhoto; // Photo loaded into application
    public String OpenedFilePath;
    public String SaveFilePath;

    // Cropping
    public Point2D CropPoint1;
    public Point2D CropPoint2;


    private Context() {
    }
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }
}
