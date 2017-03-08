import java.io.IOException;

/**
 * Created by dlo on 18/02/17.
 */
public interface CardAPI {
    public int getCardID();
    public int getCardType();
    public String getCardName();
    public void process(int playerID) throws IOException, InterruptedException;
    public int getAmount();
}

