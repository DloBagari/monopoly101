import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dice extends StackPane {

    private ImageView imageView1;
    private ImageView imageView2;
    private Timeline animation;
    private int randomNumber;
    private boolean active;
    private MonopolyClient client;
    private ArrayList<Integer> numbers;
    private long seed;
    private HBox dices;

    public Dice(MonopolyClient client) {
        this.client = client;
        numbers = new ArrayList<Integer>();
        initNumbers();
        dices = new HBox(10);
        dices.setVisible(active);
        dices.setAlignment(Pos.CENTER);
        Image image1 =  new Image("/images/d" + ((int) (Math.random() * 5) + 1) + ".png");
        Image image2 = new Image("/images/d" + ((int) (Math.random() * 5) + 1) + ".png");
        imageView1 = new ImageView(image1);
        imageView2 = new ImageView(image1);
        dices.getChildren().addAll(imageView1, imageView2);
        this.getChildren().add(dices);
        KeyFrame key = new KeyFrame(Duration.millis(2), e -> move());
        animation = new Timeline(key);
        animation.setCycleCount(360);
        dices.setOnMouseClicked(e -> {
            if (active) {
                animation.play();
                new Thread(() -> {
                    try {
                        int dice1 = 0;
                        int dice2 = 0;
                        while (true) {
                            if (animation.getStatus().toString().equals("STOPPED")) {
                                active = false;
                                randomNumber = dice1 + dice2;
                                isFinished();
                                Thread.sleep(1000);
                                dices.setVisible(false);
                                break;
                            }
                            seed = System.nanoTime();
                            Collections.shuffle(numbers, new Random(seed));
                            dice1 = numbers.get((int) (Math.random() * 6));
                            seed = System.nanoTime();
                            Collections.shuffle(numbers, new Random(seed));
                            dice2 = numbers.get((int) (Math.random() * 6));
                            imageView1.setImage(new Image("/images/d" + dice1 + ".png"));
                            imageView2.setImage(new Image("/images/d" + dice2 + ".png"));
                            Thread.sleep(30);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        });
    }

    private void initNumbers() {
        for (int i = 1; i <= 6; i++) {
            numbers.add(i);
        }
    }

    private void move() {
        imageView1.setRotate(imageView1.getRotate() + 1);
        imageView2.setRotate(imageView2.getRotate() + 1);
    }

    public int  getNumber() {
        return randomNumber;

    }

    public void isFinished() {
        client.setWaiting(false);
    }

    public void setActive(){
        active = true;
        dices.setVisible(true);
    }

}

