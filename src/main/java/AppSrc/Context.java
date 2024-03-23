package AppSrc;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


// Singleton class for current application context
public class Context {

    private static Context instance;

    Scene AppScene;

    AnchorPane root;

    public int BrightnessPercent = 100;

    public double ContrastPercent = 100.0;

    public double ExposurePercent = 100.0;

    Filters CurrentFilter;

    public Photo LoadedPhoto; // Photo loaded into application

    public String OpenedFilePath;
    public String SaveFilePath;

    private Context() {
    }
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }
}
