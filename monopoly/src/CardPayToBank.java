/**
 * Created by dlo on 17/02/17.
 */
public class CardPayToBank extends Card {
    protected int amount;

    public CardPayToBank(Bank bank, String[] lineInfo) {
        super(bank,Integer.parseInt(lineInfo[0].trim()),  Integer.parseInt(lineInfo[1].trim()), lineInfo[2]);
        this.amount = Integer.parseInt(lineInfo[3].trim());
    }

    public void process( int playerID) {
        bank.payToBank(playerID, amount, "<= €" + amount + " to bank: " + cardName + "\n");
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
