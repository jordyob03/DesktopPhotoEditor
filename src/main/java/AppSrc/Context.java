package AppSrc;

import InputMonitor.MouseEventHandler;
import UI.UIHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.scene.image.ImageView;
import java.util.Stack;

// Singleton class for current application context
public class Context {

    private static Context instance;

    public Scene AppScene;
    public ImageView imageView;

    // Command stack
    public Stack<Command> CommandStack = new Stack<>();

    // Handlers
    public MouseEventHandler EventHandler = new MouseEventHandler();
    public UI.UIHandler UIHandler = new UIHandler();

    // Image
    public boolean ImageLoaded;
    public Photo LoadedPhoto;
    public Point2D ImageTopLeft;
    public double ImageWidth = 0.0;
    public double ImageHeight = 0.0;

    // Lighting
    public double BrightnessPercent = 100.0;
    public double ContrastPercent = 100.0;
    public double ExposurePercent = 100.0;

    // Drawing
    public Color MarkerColor;
    public ArrayList<Point2D> MarkedPixels = new ArrayList<>();

    // Filters
    Filters CurrentFilter;

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
