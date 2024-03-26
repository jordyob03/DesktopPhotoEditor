package InputMonitor;
import AppSrc.Context;
import AppSrc.CropCommand;
import AppSrc.DrawCommand;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;


public class MouseEventHandler {

    public void HandleMouseClickCrop(MouseEvent event, Context AppContext){
        // Set first crop point
        AppContext.CropPoint1 = new Point2D(event.getSceneX(), event.getSceneY());

        // Get image coordinates and dimensions relative to screen
        AppContext.ImageTopLeft = AppContext.imageView.localToScreen(0, 0);

        Point2D ImageViewBottomRightScreen = AppContext.imageView.localToScreen(AppContext.imageView.getBoundsInLocal().getWidth(), AppContext.imageView.getBoundsInLocal().getHeight());
        AppContext.ImageWidth = ImageViewBottomRightScreen.getX() - AppContext.ImageTopLeft.getX();
        AppContext.ImageHeight = ImageViewBottomRightScreen.getY() - AppContext.ImageTopLeft.getY();
    }

    public void HandleMouseDraggedCrop(MouseEvent event, Context AppContext) {

        // Current position of the mouse
        Point2D CurrentPixel = new Point2D(event.getSceneX(), event.getSceneY());

        // Update crop rectangle
        double Width = CurrentPixel.getX() - AppContext.CropPoint1.getX() ;
        double Height = CurrentPixel.getY()  - AppContext.CropPoint1.getY();
        double X = Width > 0 ? AppContext.CropPoint1.getX() : CurrentPixel.getX();
        double Y = Height > 0 ? AppContext.CropPoint1.getY() : CurrentPixel.getY();
        AppContext.UIHandler.UpdateRectangle(Math.abs(Width), Math.abs(Height), X, Y);
    }
    public void HandleMouseReleasedCrop(MouseEvent event, Context AppContext) {

        // Get second cropping point
        AppContext.CropPoint2 = new Point2D(event.getSceneX(), event.getSceneY());

        AppContext.AppScene.setOnMousePressed(null);
        AppContext.AppScene.setOnMouseDragged(null);
        AppContext.AppScene.setOnMouseReleased(null);

        // Create cropping command
        CropCommand newCommand = new CropCommand(AppContext);
        newCommand.Execute();
        AppContext.CommandStack.push(newCommand);
        AppContext.UIHandler.updateDisplayedImage(AppContext);

        // Reset cropping rectangle
        AppContext.UIHandler.UpdateRectangle(0,0,0,0);
    }

    public void HandleMouseDraggedDraw(MouseEvent event, Context AppContext) {
        // Add touched pixels to list
        Point2D CurrentPixel = new Point2D(event.getSceneX(), event.getSceneY());
        AppContext.MarkedPixels.add(CurrentPixel);
    }

    public void HandleMouseReleasedDraw(MouseEvent event, Context AppContext) {

        // Set image parameters
        AppContext.ImageTopLeft = AppContext.imageView.localToScreen(0, 0);
        Point2D ImageViewBottomRightScreen = AppContext.imageView.localToScreen(AppContext.imageView.getBoundsInLocal().getWidth(), AppContext.imageView.getBoundsInLocal().getHeight());
        AppContext.ImageWidth = ImageViewBottomRightScreen.getX() - AppContext.ImageTopLeft.getX();
        AppContext.ImageHeight = ImageViewBottomRightScreen.getY() - AppContext.ImageTopLeft.getY();

        // Create draw command
        DrawCommand newCommand = new DrawCommand(AppContext);
        newCommand.Execute();
        AppContext.CommandStack.push(newCommand);
        AppContext.UIHandler.updateDisplayedImage(AppContext);

        // Reset list of pixels
        AppContext.MarkedPixels.clear();
    }
}
