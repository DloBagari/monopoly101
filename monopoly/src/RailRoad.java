/**
 * Created by dlo on 17/02/17.
 */
public class RailRoad extends PropertyAbstract implements ConstNames, PropertyAPI{
    protected int mortgage;
    protected Player owner;// make this Player

    public RailRoad(String[] info) {
        super(Integer.parseInt(info[0]),Integer.parseInt(info[1]), info[2]);

        this.mortgage = Integer.parseInt(info[3]);

    }

    public int getRent() {
        if (owner.getNumberOfTrains() == 3)
            return 100;
      return owner.getNumberOfTrains() * 25;
    }

    public void soldTo( Player player) {
        owner = player;
        status = player.getPlayerID();
        owner.addTrain();
    }

    @Override
    public void setOwner(Player player) {
        owner = player;
    }

    @Override
    public int getColor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMortgagePrice() {
        return mortgage;
    }

    public Player getOwner() {
        return owner;
    }
}
