import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Created by Cathal on 14/02/2017.
 */
public abstract class Card implements CardAPI  {
    protected String cardName;
    protected int cardID;
    protected Bank bank;
    protected  int cardType;

    public Card(Bank bank,int cardType, int cardID, String cardName){
        this.bank = bank;
        this.cardID = cardID;
        this.cardName = cardName;
        this.cardType = cardType;
    }

    @Override
    public abstract int getAmount();

    public int getCardID() {
        return cardID;
    }

    public int getCardType() {
        return cardType;
    }

    @Override
    public String getCardName() {
        return cardName;
    }

    @Override
    public abstract void process(int playerID) throws IOException, InterruptedException;
}
