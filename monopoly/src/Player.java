import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dlo on 06/02/17.
 */
public class Player {
    private Socket privateSocket;
    private Socket positionSocket;
    private Socket notificationSocket;
    private Socket numberOfPlayersSocket;
    private Socket terminationSocket;
    private DataOutputStream sendDataByPositionSocket;
    private DataInputStream receiveDataByPositionSocket;
    private ObjectOutputStream sendObjectByPositionSocket;
    private ObjectOutputStream sendObjectByPrivateSocket;
    private DataInputStream receiveDataByPrivateSocket;
    private DataOutputStream sendDataByPrivateSocket;
    private DataInputStream receiveDataByNotificationSocket;
    private DataOutputStream sendDataByNotificationSocket;
    private ObjectOutputStream sendObjectByNotificationSocket;
    private DataOutputStream sendDataByNumberOfPlayerSocket;
    private DataOutputStream sendDataByTerminationSocket;
    private ObjectOutputStream sendObjectByTerminationSocket;
    private DataInputStream receiveDataByTerminationSocket;
    private String token;
    private int numberOfUtilityOwned;
    private int[] colorsOwned = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    private int numberOfTrainsOwned;
    private boolean inJail;
    private int playerID;
    private int numberOfHouses;
    private int numberOfHotels;
    private ArrayList<Integer> ownedProperties = new ArrayList<>();
    private int investment;
    private boolean alive = true;

    private int gameStage = 0;
    public Player(Socket privateSocket, Socket positionSocket,
                  Socket notificationSocket, Socket numberOfPlayersSocket) throws IOException {
        this.numberOfPlayersSocket = numberOfPlayersSocket;
        this.privateSocket = privateSocket;
        this.positionSocket = positionSocket;
        this.notificationSocket = notificationSocket;
        initNotificationSocket();
        initPositionSocket();
        initPrivateSocket();
        initNumberOfPlayersSocket();

    }

    public void setPlayerID(int id) {
        playerID = id;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setInJail(boolean value) {
        inJail = value;
    }

    public boolean getInJail() {
        return inJail;
    }

    public void addTrain() {
        numberOfTrainsOwned++;
    }

    public int getNumberOfTrains() {
        return numberOfTrainsOwned;
    }

    public void addColor(int colorCode) {
        colorsOwned[colorCode]++;
    }

    public int getcolor(int colorCode) {
        return colorsOwned[colorCode];
    }
    public void addUtility() {
        numberOfUtilityOwned++;
    }

    public int getNumberOfUtility() {
        return numberOfUtilityOwned;
    }
    private void initPositionSocket() throws IOException {
        sendDataByPositionSocket = new DataOutputStream(positionSocket.getOutputStream());
        receiveDataByPositionSocket = new DataInputStream(positionSocket.getInputStream());
        sendObjectByPositionSocket = new ObjectOutputStream(positionSocket.getOutputStream());

    }

    private void initPrivateSocket() throws IOException {
        sendObjectByPrivateSocket = new ObjectOutputStream(privateSocket.getOutputStream());
        receiveDataByPrivateSocket = new DataInputStream(privateSocket.getInputStream());
        sendDataByPrivateSocket = new DataOutputStream(privateSocket.getOutputStream());
    }

    private void initNotificationSocket() throws  IOException {
        receiveDataByNotificationSocket =
                new DataInputStream(notificationSocket.getInputStream());
        sendDataByNotificationSocket =
                new DataOutputStream(notificationSocket.getOutputStream());
        sendObjectByNotificationSocket =
                new ObjectOutputStream(notificationSocket.getOutputStream());
    }

    private void initNumberOfPlayersSocket() throws IOException {
        sendDataByNumberOfPlayerSocket = new DataOutputStream(numberOfPlayersSocket.getOutputStream());
    }

    public String getPlayerAddress() {
        return privateSocket.getInetAddress().toString();
    }

    public void SendIntToPositionSocket(int value) throws IOException {
        sendDataByPositionSocket.writeInt(value);
    }

    public int recevieIntFromPostionSocket() throws IOException {
        return receiveDataByPositionSocket.readInt();
    }

    public int receiveIntFromPrivateSocket() throws IOException {
        return receiveDataByPrivateSocket.readInt();
    }

    public String receiveStringFromPrivateSocket() throws IOException {
        return receiveDataByPrivateSocket.readUTF();
    }

    public void sendIntToPrivateSocket(int value) throws IOException {
        sendDataByPrivateSocket.writeInt(value);
    }

    public void goToNextStage() {
        gameStage++;
    }

    public int getGameStage() {
        return gameStage;
    }

    public void sendObjectToPrivateSocket(ArrayList<String> tokens) throws IOException {
        sendObjectByPrivateSocket.writeObject(tokens);
        sendObjectByPrivateSocket.flush();
    }

    public void sendObjectToNotificationSocket(ArrayList<String> list)  throws IOException{
        sendObjectByNotificationSocket.writeObject(list);
        sendObjectByNotificationSocket.flush();
    }

    public void sendObjectToPositionSocket(Integer[] list)  throws IOException{
        sendObjectByPositionSocket.writeObject(list);
        sendObjectByPositionSocket.flush();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String receiveStringFromNotificationSocket() throws IOException {
        return receiveDataByNotificationSocket.readUTF();
    }

    public void sendStringToNotificationSocket(String value) throws IOException {
        sendDataByNotificationSocket.writeUTF(value);
        sendDataByNotificationSocket.flush();

    }

    public void  sendIntToNumberOfPlayersSocket(int value) throws IOException {
        sendDataByNumberOfPlayerSocket.writeInt(value);
        sendDataByNumberOfPlayerSocket.flush();
    }

    public String getToken() {
        return token;
    }


    public void sendStringToPrivateSocket(String value) throws IOException {
        sendDataByPrivateSocket.writeUTF(value);
    }

    public void sendIntToNotificationSocket(int value) throws IOException {
        sendDataByNotificationSocket.writeInt(value);
    }

    public void sendIntToPositionSocket(int value) throws IOException {
        sendDataByPositionSocket.writeInt(value);
    }

    public void renewPrivateSocket(Socket socket) throws IOException {
        privateSocket.close();
        privateSocket = socket;
        initPrivateSocket();
    }

    public void renewNotificationSocket(Socket socket) throws IOException {
        notificationSocket.close();
        notificationSocket = socket;
        initNotificationSocket();
    }

    public void renewPositionSocket(Socket socket) throws  IOException{
        positionSocket.close();
        positionSocket = socket;
        initPositionSocket();
    }


    public void renewNumberOfPlayersSocket(Socket socket) throws IOException {
        numberOfPlayersSocket.close();
        numberOfPlayersSocket = socket;
        initNumberOfPlayersSocket();
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public int getNumberOfHotels() {
        return numberOfHotels;
    }

    public void addProperty(Integer propertyID) {
        ownedProperties.add(propertyID);
    }

    public void addInvestment(int value) {
        investment += value;
    }

    public int getInvestment() {
        return investment;
    }

    public void sendStringToNumberOfPlayerSocket(String statement) throws IOException {
        sendDataByNumberOfPlayerSocket.writeUTF(statement);
    }

    public void sendObjectToPrivateSocket(List<Integer> list) throws IOException {
        sendObjectByPrivateSocket.writeObject(list);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
          return true;
      if (o == null)
          return false;
      if (getClass() != o.getClass())
          return false;
      Player player = (Player)o;
      return this.getPlayerID() == player.getPlayerID();
    }

    public ArrayList<Integer> getOwnedProperties() {
        return (ArrayList<Integer>) ownedProperties.clone();
    }

    public void removeProperty(int mortgageProperty) {
        ownedProperties.remove(new Integer(mortgageProperty));
    }

    public int getNumberOfProperties() {
        return ownedProperties.size();
    }

    public boolean isAlive() {
        return alive;
    }

    public void lost() {
        alive = false;
    }

    public void closeSockets() throws IOException {
        privateSocket.close();
        numberOfPlayersSocket.close();
        notificationSocket.close();
        positionSocket.close();
        terminationSocket.close();
    }

    public void renewTerminationSocket(Socket socket) throws IOException {
        terminationSocket = socket;
        sendDataByTerminationSocket = new DataOutputStream(terminationSocket.getOutputStream());
        sendObjectByTerminationSocket = new ObjectOutputStream(terminationSocket.getOutputStream());
        receiveDataByTerminationSocket = new DataInputStream(terminationSocket.getInputStream());
    }

    public void sendIntToTerminationSocket(int value) throws IOException {
        sendDataByTerminationSocket.writeInt(value);
    }

    public int receiveIntFromTerminationSocket() throws IOException {
        return receiveDataByTerminationSocket.readInt();
    }

    public void sendObjectToTerminationSocket(ArrayList<Integer> list) throws IOException {
        sendObjectByTerminationSocket.writeObject(list);
        sendObjectByTerminationSocket.flush();
    }
}
