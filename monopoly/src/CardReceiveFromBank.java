/**
 * Created by dlo on 17/02/17.
 */
public class CardReceiveFromBank extends  Card {
    private int amount;
    public CardReceiveFromBank(Bank bank,String[] lineInfo) {
        super(bank, Integer.parseInt(lineInfo[0]),  Integer.parseInt(lineInfo[1]), lineInfo[2]);
        this.amount = Integer.parseInt(lineInfo[3]);
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void process(int playerID) {
        bank.payTO(playerID, amount, "=> €" + amount + " from bank: " + cardName + "\n");
    }
}
