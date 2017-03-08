import javax.crypto.AEADBadTagException;
import java.util.ArrayList;

/**
 * Created by Cathal on 31/01/2017.
 */
public class Property extends PropertyAbstract {
    private int color;
    private Player owner;
    private int  houses;
    private int hotels;
    private int[] rentalPrices;
    private int mortgage;
    private int houseCost;
    private int hotelCost;
    public Property(String[] info) {
        super(Integer.parseInt(info[0]), Integer.parseInt(info[1]), info[2]);
        this.rentalPrices = new int[6];
        this.color = Integer.parseInt(info[3]);
        this.rentalPrices[0] = Integer.parseInt(info[4]);
        this.rentalPrices[1] = Integer.parseInt(info[5]);
        this.rentalPrices[2] = Integer.parseInt(info[6]);
        this.rentalPrices[3] = Integer.parseInt(info[7]);
        this.rentalPrices[4] = Integer.parseInt(info[8]);
        this.rentalPrices[5] = Integer.parseInt(info[9]);
        this.mortgage = Integer.parseInt(info[10]);
        this.houseCost = Integer.parseInt(info[11]);
        this.hotelCost = Integer.parseInt(info[12]);
    }

     @Override
    public int getColor(){return color;}

    @Override
    public int getRent() {
        if (hotels != 0)
            return rentalPrices[5];
        return rentalPrices[houses];
    }

    public void addHouse() {
        houses++;
    }

    public void addHotel() {
        hotels++;
    }

    @Override
    public void setOwner(Player player) {
        owner = player;
    }

    public int getOwnerId() {
        return owner.getPlayerID();
    }

    @Override
    public int getMortgagePrice() {
        return mortgage;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void soldTo(Player player) {
        this.owner = player;
        this.status = player.getPlayerID();
        player.addColor(this.color);
    }

}


