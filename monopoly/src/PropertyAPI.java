/**
 * Created by dlo on 18/02/17.
 */
public interface PropertyAPI {
    public String getName();
    public int getMortgagePrice();
    public int getStatus();
    public void setStatus(int status);
    public int getRent();
    public void setOwner(Player player);
    public Player getOwner();
    public int getPropertyID();
    public int getColor();
    public void soldTo(Player player);
}
