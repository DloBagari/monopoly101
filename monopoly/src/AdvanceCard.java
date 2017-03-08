/**
 * Created by dlo on 17/02/17.
 */
public class AdvanceCard extends Card {
    public AdvanceCard(Bank bank, String[] lineInfo) {
        super(bank,  Integer.parseInt(lineInfo[0]),  Integer.parseInt(lineInfo[1]), lineInfo[2]);
    }

    @Override
    public void process(int playerID) {

    }

    @Override
    public int getAmount() {
        throw new UnsupportedOperationException();
    }
}
