/**
 * Created by dlo on 17/02/17.
 */
public class PropertyDemo extends PropertyAbstract {

    public PropertyDemo(String[] info) {
        super(Integer.parseInt(info[0]), Integer.parseInt(info[1]), info[2]);
    }

    @Override
    public int getColor(){
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int getMortgagePrice() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRent() {
        throw new UnsupportedOperationException();    }

    @Override
    public void setOwner(Player player) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Player getOwner() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void soldTo(Player player) {
        throw new UnsupportedOperationException();
    }
}
