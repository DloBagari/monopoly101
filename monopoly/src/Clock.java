import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by dlo on 24/02/17.
 */
public class Clock extends StackPane implements ConstNames{
    private final int SECONDS = 6;
    int count;
    Arc[] arcs = new Arc[SECONDS];
    Label text;
    FadeTransition fadeTransition;
    Timeline animation;
    MonopolyClient board;
    private boolean passed = false;

    public Clock(MonopolyClient board) {
        this.board = board;
        this.setMaxWidth(200);
        this.setMinWidth(200);
        this.setMaxHeight(230);
        this.setMinHeight(230);
        this.setPrefSize(200, 230);
        Pane pane = new Pane();
        fadeTransition = new FadeTransition(Duration.millis(100));
        fadeTransition.setFromValue(0.4);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(2);
        text = new Label("");
        text.setFont(Font.font(50));
        int a = 90;
        int b = -45;
        for (int i = 0; i < 6 ; i++) {
            Arc arc = new Arc(100, 115, 70, 70, a, b);
            arcs[i] = arc;
            arc.setVisible(false);
            a -=60;
            arc.setType(ArcType.OPEN);
            //arc.setType(ArcType.OPEN);
            //arc.setType(ArcType.CHORD);
            arc.setStroke(Color.RED);
            arc.setStyle("-fx-border-width:30;");
            arc.setStrokeWidth(10);
            pane.getChildren().addAll(arc);
        }
        this.getChildren().addAll(pane, text);
        KeyFrame key =
                new KeyFrame(Duration.millis(1000), e -> {
                    try {
                        move();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
        animation = new Timeline(key);
        animation.setCycleCount(SECONDS);
    }

    public void move() throws IOException {
        fadeTransition.setNode(arcs[count]);
        fadeTransition.play();
        arcs[count].setVisible(true);
        text.setText(count++ + 1 + "");
        if (count == SECONDS) {
            animation.stop();
            if (!passed)
                board.sendIntToPrivateSocket(PASS);
        }


    }

    public void reset() {
        stop();
        play();
    }

    public void play() {
        animation.play();
    }

    public void stop() {
        animation.stop();
        text.setText("");
        for (int i = 0; i < arcs.length; i++) {
            arcs[i].setVisible(false);
        }
        count = 0;
    }

    public void setPassed(boolean value) {
        passed = value;
    }



}
