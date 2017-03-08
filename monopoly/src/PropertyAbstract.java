/**
 * Created by dlo on 17/02/17.
 */
public abstract class PropertyAbstract implements  PropertyAPI {
    protected int propertyID;
    protected  int status;
    protected String name;

    public PropertyAbstract(int propertyID, int status, String name){
        this.status = status;
        this.name = name;
        this.propertyID = propertyID;
    }
    @Override
    public int  getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public abstract int getColor();

    @Override
    public String getName(){
        return name;
    }

    @Override
    public int getPropertyID() {
        return propertyID;
    }

    @Override
    public abstract int getMortgagePrice();

    @Override
    public abstract int getRent();

    @Override
    public abstract void soldTo(Player player);

    @Override
    public abstract void setOwner(Player player);

    @Override
    public abstract Player getOwner();
}
