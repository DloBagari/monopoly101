/**
 * Created by dlo on 17/02/17.
 */
public class Utility extends RailRoad {
    private Bank bank;

    public Utility(Bank bank, String[] info) {
        super(info);
        this.bank = bank;
    }

    @Override
    public void soldTo(Player player) {
        this.owner = player;
        status = player.getPlayerID();
        this.owner.addUtility();
    }



    public int getRent() {
        int dice = bank.getDicesValue();
        if (owner.getNumberOfUtility() == 2) {
            return dice * 10;
        }
        return dice * 4;
    }

}
