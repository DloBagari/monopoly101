import javafx.animation.FadeTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by dlo on 19/02/17.
 */
public class FlashMoney extends StackPane {
    FadeTransition fadeTransition;

    public  FlashMoney(Color color) {
        Rectangle rectangle = new Rectangle(210, 100);
        rectangle.setFill(color);
        this.getChildren().add(rectangle);
        this.setVisible(false);
        fadeTransition =
                new FadeTransition(Duration.millis(200), rectangle);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(0.8);
        fadeTransition.setCycleCount(2);
    }

    public void play() throws InterruptedException {
        this.setVisible(true);
        fadeTransition.play();
        while (true) {
            if (fadeTransition.getStatus().toString().equals("STOPPED")) {
                this.setVisible(false);
                break;
            }
            Thread.sleep(200);
        }
    }

}
