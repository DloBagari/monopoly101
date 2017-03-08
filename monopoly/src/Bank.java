import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Cathal on 31/01/2017.
 */
public class Bank implements ConstNames {
    private final BoardController board;
    private int bankBalance = Integer.MAX_VALUE;
    public static final int playerStartingFunds = 1500;
    private String  banksDirectoryPath = "./banks/";
    private static int bankNumber = 1;
    private final Path bankFilePath;
    private final  Lock lock;
    private int[] bankDataBase;//amount of money each player has
    private PrintWriter[] statements;
    private final ArrayList<Player> customers;
    private List<PropertyAbstract> properties;

    public Bank(BoardController board)throws IOException {
        properties = BuildProperty.getBuildProperties(this, "cardInfo");
        lock = new ReentrantLock(true);
        this.customers = board.getPlayers();
        this.board = board;
        bankDataBase = new int[customers.size()];
        bankFilePath = Paths.get(banksDirectoryPath + "bank_" + bankNumber++);
        createBankFile();
        statements = new PrintWriter[customers.size()];
        sendStartFunds();
    }

    private void sendStartFunds() throws  IOException{
        for (int i = 0; i < customers.size(); i++) {
            bankRollPlayer(i);
        }
    }

    private void createBankFile()throws IOException {
        if (!Files.exists(bankFilePath)) {
            Files.createDirectories(bankFilePath);
        }
    }

    public void bankRollPlayer(int  customerId) throws IOException {
        PrintWriter statment = new PrintWriter(new File(bankFilePath.toString() +
                "/player"+customerId+"Statement.txt"));
        statements[customerId] = statment;
        withDraw(playerStartingFunds, customerId,
                "===> from Bank: Starting funds " + playerStartingFunds);
    }

    public void withDraw(int amount,int customerId, String reason) throws IOException {
        lock.lock();
        try{
            bankDataBase[customerId] += amount;
            statements[customerId].println(reason);
            statements[customerId].flush();
            customers.get(customerId).sendIntToNumberOfPlayersSocket(amount);
            this.bankBalance -= amount;
        }finally {
            lock.unlock();
        }
    }

    public void deposit(int amount){
        bankBalance += amount;
    }

    public boolean isForSale(int cellNumber) {
       if (((PropertyAPI)properties.get(cellNumber)).getOwner() == null)
           return true;
        return false;
    }

    public boolean isPlayerHasMoneyToBuy(int cellNumber, int p) {
        return bankDataBase[p] >= ((PropertyAPI)properties.get(cellNumber)).getMortgagePrice() * 2;

    }

    public void sellProperty(int propertyID, Player player) throws IOException {
        PropertyAPI property =  properties.get(propertyID);
        payPropertyPrice(property, player);
        property.soldTo(player);
        player.addProperty(propertyID);
    }
    public void sellPropertyAuction(int propertyID, Player player, int amount){
        PropertyAPI property =  properties.get(propertyID);
        payPropertyPriceAuction(property, player, amount);
        property.soldTo(player);
        player.addProperty(propertyID);
    }

    private  void  payPropertyPriceAuction(PropertyAPI property, Player player, int amount){
        lock.lock();
        try {
            String statement = "<= €" + amount +
                    " purchase Property " + property.getName() + "\n";
            statements[player.getPlayerID()].println(statement);
            statements[player.getPlayerID()].flush();
            bankDataBase[player.getPlayerID()] -= amount;
            this.bankBalance += amount;
            customers.get(player.getPlayerID()).addInvestment(amount);
            sendStatement(player, statement);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void payPropertyPrice(PropertyAPI property, Player player) throws IOException{
        lock.lock();
        try {
            String statement = "<= €" + (property.getMortgagePrice() * 2) +
                    " purchase Property " + property.getName() + "\n";
            statements[player.getPlayerID()].println(statement);
            statements[player.getPlayerID()].flush();
            bankDataBase[player.getPlayerID()] -= property.getMortgagePrice() * 2;
            this.bankBalance += property.getMortgagePrice() * 2;
            customers.get(player.getPlayerID()).addInvestment(property.getMortgagePrice() * 2);
            sendStatement(player, statement);
        }finally {
            lock.unlock();
        }
    }

    private void sendStatement(Player player, String statement) throws IOException {
        if (player.isAlive())
        player.sendStringToNumberOfPlayerSocket(statement);
    }

    public void sendAmountMoney( int p) throws IOException {
        customers.get(p).sendIntToPrivateSocket(bankDataBase[p]);
    }

    public void playRent(int p, int cellNumber) {
        lock.lock();
        try {
            int rent = properties.get(cellNumber).getRent();
            int ownerId = properties.get(cellNumber).getOwner().getPlayerID();
            bankDataBase[p] -= rent;
            bankDataBase[ownerId] += rent;
            String statement1 = "<= €" + rent  + " rent to player" + ownerId + ": "
                    + properties.get(cellNumber).getName() + "\n";
            String statement12 = "=> €" + rent+ " rent from player" + p + ": "  +
                    properties.get(cellNumber).getName() + "\n";
            statements[p].println(statement1);
            statements[p].flush();
            statements[ownerId].println(statement12);
            statements[ownerId].flush();
            sendStatement(customers.get(p), statement1);
            sendStatement(customers.get(ownerId), statement12);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public int getPlayerBalance(int playerID) {
        return bankDataBase[playerID];
    }

    public void payTO(int player, int amount, String s) {
        lock.lock();
        try {
            bankBalance -= amount;
            bankDataBase[player] += amount;
            statements[player].println(s);
            statements[player].flush();
            sendStatement(customers.get(player), s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void payToBank(int player, int amount, String s) {
        lock.lock();
        try {
            bankBalance += amount;
            bankDataBase[player] -= amount;
            statements[player].println(s);
            statements[player].flush();
            sendStatement(customers.get(player), s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public int getPropertyStatus(int newPosition) {
        return properties.get(newPosition).getStatus();
    }

    public void playerToPlayer(int fromPlayer, int toPlayer , int amount,
                               String fromPlayerStatement, String toPlayerStatement) {
        if (bankDataBase[fromPlayer] < amount) {
            noEnoughMoney(fromPlayer);
        }
        lock.lock();
        try {
            bankDataBase[fromPlayer] -= amount;
            statements[fromPlayer].println(fromPlayerStatement);
            statements[fromPlayer].flush();
            bankDataBase[toPlayer] += amount;
            statements[toPlayer].println(toPlayerStatement);
            statements[toPlayer].flush();
            sendStatement(customers.get(fromPlayer), fromPlayerStatement);
            sendStatement(customers.get(toPlayer), toPlayerStatement);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

    private void noEnoughMoney(int playerID) {
    }

    public int customersNumber() {
        return customers.size();
    }

    public Player getPlayer(int playerID) {
        return customers.get(playerID);
    }

    public String getPropertyName(int p) {
        return properties.get(p).getName();
    }

    public void movePlayerTo(int property) throws IOException, InterruptedException {
        board.movePlayerTo(property);
    }

    public Player getPropertyOwner(int propertyID) {
        return properties.get(propertyID).getOwner();
    }

    public int getPropertyRent(int propertyID) {
        return properties.get(propertyID).getRent();
    }


    public int getDicesValue() {
        return board.getDicesValue();
    }

    public int getMortgage(int cellNumber) {
        return properties.get(cellNumber).getMortgagePrice();
    }

    public void setPropertyStatus(int mortgageProperty, int mortgage) {
        properties.get(mortgageProperty).setStatus(mortgage);
    }
}
