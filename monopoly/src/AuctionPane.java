import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by dlo on 22/02/17.
 */
public class AuctionPane extends BorderPane implements ConstNames  {
    private MonopolyClient board;
    private int myOffer;
    private int currentOffer;
    private Label  currentPrice;
    private Clock clock;

    public AuctionPane(MonopolyClient board, int mortgage) {
        this.setStyle("-fx-background-color:white;");
        this.board = board;
        this.setMaxWidth(200);
        this.setMinWidth(200);
        this.setMaxHeight(278);
        this.setMinHeight(278);
        this.setPrefSize(200, 278);
        clock = new Clock(board);
        currentOffer = mortgage;
        currentPrice = new Label("Starting price is: " + mortgage);
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        setTop(currentPrice);
        Button pass = new Button("Pass");
        pass.setOnAction(e -> {
            try {
                onPassClick();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        buttons.getChildren().add(pass);
        Button increaseByTen = new Button("Offer +10");
        increaseByTen.setOnAction(e -> {
            try {
                onIncreaseClick();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        buttons.getChildren().add(increaseByTen);
        setBottom(buttons);
        setCenter(clock);
        clock.play();

    }

    private void onIncreaseClick() throws IOException {
        if (myOffer < currentOffer && board.getMoneyAmount() >= currentOffer + 10 ) {
            myOffer = currentOffer + 10;
            clock.stop();
            board.sendIntToPrivateSocket(myOffer);
        }

    }

    private void onPassClick() throws IOException {
        clock.stop();
        clock.setPassed(true);
        board.sendIntToPrivateSocket(PASS);
    }

    public void setLastOffer(int lastOffer) throws IOException {
        if (lastOffer < 0) {
            currentPrice.setText("You have no enough money!");
            clock.setPassed(true);
            board.sendIntToPrivateSocket(PASS);
        }

        else {
            currentOffer = lastOffer;
            currentPrice.setText("Current Offer is: " + currentOffer);
        }
    }


    public void play() {
        clock.play();
    }

    public void reset() {
        clock.reset();
    }

    public void setPassed(boolean value) {
        clock.setPassed(value);
    }
}
