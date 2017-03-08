/**
 * Created by dlo on 17/02/17.
 */
public class CardReceiveFromEveryPlayer extends Card {
    private int amount;
    public CardReceiveFromEveryPlayer(Bank bank,String[] lineInfo) {
        super(bank,Integer.parseInt(lineInfo[0]),  Integer.parseInt(lineInfo[1]), lineInfo[2]);
        this.amount = Integer.parseInt(lineInfo[3]);
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void process(int playerID) {
        for (int p = 0; p < bank.customersNumber(); p++) {
            if (p != playerID)
                bank.playerToPlayer(p, playerID, amount,
                        "<= €" + amount + " to player " + playerID + ": " + cardName + "\n",
                        "=> €" + amount + "from player " + p + ": " + cardName + "\n");
        }
        System.out.print("processing receive from all");
    }
}
