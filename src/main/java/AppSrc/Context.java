package AppSrc;

// Singleton class for current application context
public class Context {

    private static Context instance;

    public int BrightnessPercent = 100;

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
