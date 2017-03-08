/**
 * Created by dlo on 17/02/17.
 */
public class StreetRepairs extends Card {
    private int amountForHouse;
    private int amountForHotel;
    public StreetRepairs(Bank bank, String[] lineInfo) {
        super(bank, Integer.parseInt(lineInfo[0]),  Integer.parseInt(lineInfo[1]), lineInfo[2]);
        this.amountForHouse = Integer.parseInt(lineInfo[3]);
        this.amountForHotel = Integer.parseInt(lineInfo[4]);
    }

    @Override
    public int getAmount() {
        throw new UnsupportedOperationException();
    }

    public void process(int playerID) {
        int amount  = bank.getPlayer(playerID).getNumberOfHouses() * amountForHouse +
                bank.getPlayer(playerID).getNumberOfHotels() * amountForHotel;
        bank.payTO(playerID, amount, "=> €" + amount + " from bank: " + cardName + "\n");
    }
}
