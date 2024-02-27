module  AppSrc{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;



    opens AppSrc to javafx.fxml;
    exports AppSrc;

}