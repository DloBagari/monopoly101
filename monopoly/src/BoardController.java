import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public  class BoardController implements Runnable, ConstNames{
    private Integer[] playersPositionOnBoard;
    private ArrayList<String> tokensName = new ArrayList<>();
    private ServerSocket serverSocket;
    private ArrayList<Player> players;
    private Bank bank;
    private LinkedList<Card> chanceCards;
    private  LinkedList<Card> treasureCards;
    private int whoIsTurn;
    private int playerMove;
    private Player auctionPlayer;
    private int auctionOffer;
    private int newPosition;
    private boolean waiting = true;
    private List<Thread> auctionThreads;

    public BoardController(ArrayList<Player> players) throws IOException {
        super();
        this.players = players;
        playersPositionOnBoard = new Integer[players.size()];
        this.bank = new Bank(this);
    }

    @Override
    public void run(){
        try {
            //send notification game is started

            for (Player p : players) {
                p.sendStringToNotificationSocket("gameStarted");
            }
            Thread.sleep(500);
            //renew sockets
            renewSockets();
            Thread.sleep(500);
            sendPlayerNumbersAndTokens();
            sendStartingFunds();
            //free the buffer of numberOfPlayerSocket
            for (int k = 0; k < players.size(); k++) {
                players.get(k).sendIntToNumberOfPlayersSocket(-1);
            }
            Thread.sleep(200);
            initCards();

            while(true) {
                for (Player player : players) {
                    if (player.isAlive()) {
                        whoIsTurn = player.getPlayerID();
                        for (Player p : players) {
                            if (p.isAlive()) {
                                p.sendStringToPrivateSocket("FREE");
                                p.sendStringToPrivateSocket("Player " + (whoIsTurn + 1) + " Turn");
                            }
                        }
                        playerMove = player.receiveIntFromPrivateSocket();
                        while (playerMove < 0)
                            playerMove = player.receiveIntFromPrivateSocket();
                        newPosition = (playerMove + playersPositionOnBoard[whoIsTurn]) % 40;
                        sendNewPosition(newPosition, bank.getPropertyStatus(newPosition));
                        Thread.sleep(1000);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendNewPosition(int newPosition, int status) throws IOException, InterruptedException {
        if (playersPositionOnBoard[whoIsTurn] + playerMove > 41 || newPosition == 0)
            bank.payTO(whoIsTurn, 200, "=> 200 from bank: pass go\n");
        playersPositionOnBoard[whoIsTurn] = newPosition;
        for (Player player : players) {
            if (player.isAlive()) {
                player.sendIntToPositionSocket(status);
                player.sendIntToPositionSocket(whoIsTurn);
                player.sendIntToPositionSocket(newPosition);
            }
        }

        Thread.sleep(100);
        processPropertyStatus(status, newPosition , whoIsTurn);
        for (Player player : players) {
            if (player.isAlive())
                player.sendIntToNotificationSocket(bank.getPlayerBalance(player.getPlayerID()));
        }
    }

    private void sendNewPositionToOnePlayer(int newPosition, int status ,Player player) throws IOException, InterruptedException {
        playersPositionOnBoard[whoIsTurn] = newPosition;
        player.sendIntToPositionSocket(status);
        player.sendIntToPositionSocket(whoIsTurn);
        player.sendIntToPositionSocket(newPosition);
    }

    private void processPropertyStatus(int propertyStatus, int newPosition, int whoIsTurn)
            throws IOException, InterruptedException {
        int accept;
        int moneyCheck;
        Player player = players.get(whoIsTurn);
        switch (propertyStatus) {
            case NO_OWNER: case RIALROAD:case UTILITY:
                moneyCheck = bank.getPlayerBalance(whoIsTurn) -
                        (bank.getMortgage(newPosition) * 2);
                player.sendIntToPrivateSocket(moneyCheck);
                accept = player.receiveIntFromPrivateSocket();
                int cellNumber = player.receiveIntFromPrivateSocket();
                if (accept == ACCEPTED) {
                    if (bank.isForSale(cellNumber) && bank.isPlayerHasMoneyToBuy(cellNumber,
                            whoIsTurn)) {
                        bank.sellProperty(cellNumber, player);
                        player.sendIntToPositionSocket(UNLOCKQUEUE);
                        player.sendIntToPositionSocket(whoIsTurn);
                        player.sendIntToPositionSocket(newPosition);
                    }
                }
                else if (accept == AUCTION) {
                    int mortgage = bank.getMortgage(cellNumber);
                    auctionOffer = mortgage;
                    auctionPlayer =null;
                    auctionThreads = new ArrayList<>();
                    for (Player p : players) {
                        if (!p.equals(player) && p.isAlive()) {
                            p.sendStringToPrivateSocket("FREE");
                            p.sendStringToPrivateSocket("Player " + (whoIsTurn + 1) + " Turn");
                        }
                    }
                    sendNewPosition(newPosition, AUCTION);
                    for (Player p : players) {
                        if (p.isAlive()) {

                            p.sendIntToTerminationSocket(mortgage * 2);
                            Thread auctionTracker = new Thread(new Auction(p));
                            auctionThreads.add(auctionTracker);
                            auctionTracker.start();
                        }
                    }
                    new Thread(() -> {
                        try {

                            mainLoop:
                            while (true) {
                                boolean isAuctionThreadsDone =true;
                                innerLoop:
                                for (Thread thread : auctionThreads) {
                                    if (thread.isAlive()) {
                                        isAuctionThreadsDone = false;
                                        break innerLoop;
                                    }
                                }
                                if (isAuctionThreadsDone) {
                                    int owner;
                                    if (auctionOffer != 0 && auctionPlayer != null) {
                                        bank.sellPropertyAuction(newPosition,
                                                auctionPlayer, auctionOffer);
                                        owner = auctionPlayer.getPlayerID();
                                    }else
                                        owner= NO_OWNER;
                                    for(Player p : players) {
                                        if (p.isAlive()) {
                                            p.sendIntToTerminationSocket(TERMINATE_AUCTION);
                                            p.sendIntToTerminationSocket(owner);
                                        }
                                    }
                                    waiting = false;
                                    break mainLoop;
                                }
                                Thread.sleep(500);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    waitToFinishAuction();
                }

                break;
            //no needed delete??
            case NOT_PROPERTY:
                bank.sendAmountMoney(whoIsTurn);
                break;
            case 0:case 1:case 2:case 3:case 4:case 5:case 6:case 7:
                player.sendStringToPrivateSocket("Pay €" +
                bank.getPropertyRent(newPosition) +" rent of " + bank.getPropertyName(newPosition) + ": ");
                int AcceptPay = player.receiveIntFromPrivateSocket();
                if (AcceptPay == ACCEPTED) {
                    if (processMortgage(bank.getPropertyRent(newPosition), player)) {
                        bank.playRent(whoIsTurn, newPosition);
                        sendNewPosition(newPosition, PASS);
                    }
                }
                break;
            case INCOME_TAX:
                accept = player.receiveIntFromPrivateSocket();
                if (accept == ACCEPTED) {
                    bank.payToBank(whoIsTurn, (int)(player.getInvestment() * 0.10),
                            "<= €" + (int)(player.getInvestment() * 0.10) +
                                    " to bank " + bank.getPropertyName(newPosition) + "\n");
                }
                break;
            case LUXURY_TAX:
                accept = player.receiveIntFromPrivateSocket();
                if (accept == ACCEPTED) {
                    bank.payToBank(whoIsTurn, 75,
                            "<= €" + 75 +
                                    " to bank " + bank.getPropertyName(newPosition) + "\n");
                }
                break;
            case CHANCE_CARD:
                CardAPI chanceCard = getChanceCard();
                player.sendIntToTerminationSocket(chanceCard.getCardID());
                player.sendIntToTerminationSocket(chanceCard.getCardType());
                accept = player.receiveIntFromTerminationSocket();
                Thread.sleep(500);
                if (accept == ACCEPTED) {
                    if (chanceCard.getCardType() == PAY_TO_BANK ) {
                        processMortgage(chanceCard.getAmount(), player);
                        sendNewPosition(newPosition, PASS);
                    }
                    //pay,receive operation
                    chanceCard.process(whoIsTurn);
                }
                break;
            case COMM_CHEST:
                CardAPI card = getCommunityCard();
                //send card Id
                player.sendIntToPrivateSocket(card.getCardID());
                //receive Acceptance
                accept = player.receiveIntFromPrivateSocket();
                if (accept == ACCEPTED) {
                    //pay,receive operation
                    card.process(whoIsTurn);
                }
                break;
        }
    }

    private boolean processMortgage(int paymentAmount, Player player) throws IOException, InterruptedException {
        while (paymentAmount > bank.getPlayerBalance(whoIsTurn)) {
            if (player.getNumberOfProperties() == 0) {
                //send notification to all player to terminate lost player.
                sendNewPosition(newPosition, LOST);
                for (Player p : players)
                    if (p.isAlive()) {

                        p.sendIntToTerminationSocket(player.getPlayerID());
                    }
                player.lost();
                player.closeSockets();

                return false;
            }
            sendNewPositionToOnePlayer(newPosition, NO_ENOUGH_MONEY, player);
            player.sendObjectToTerminationSocket(
                    (ArrayList<Integer>)player.getOwnedProperties().clone());
            int mortgageProperty = player.receiveIntFromPrivateSocket();
            bank.setPropertyStatus(mortgageProperty, MORTGAGE);
            player.removeProperty(mortgageProperty);
            bank.payTO(whoIsTurn, bank.getMortgage(mortgageProperty),
                    "=> " + bank.getMortgage(mortgageProperty) +
                            " Mortgage property " + bank.getPropertyName(mortgageProperty) + "\n");
        }
        return  true;
    }

    private void waitToFinishAuction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    private Card getCommunityCard() {
        Card card = treasureCards.removeFirst();
        treasureCards.addLast(card);
        return  card;
    }

    private Card getChanceCard() {
        Card card = chanceCards.removeFirst();
        chanceCards.addLast(card);
        return  card;
    }

    private void initCards() throws IOException {
        chanceCards = CreateCardObjects.makeChanceCards(bank, "CHANE_CARDS.txt");
        Collections.shuffle(chanceCards);
        treasureCards = CreateCardObjects.makeCommunityCards(bank, "COMMUNITY_CARDS.txt");
        Collections.shuffle(treasureCards);
    }

    private void sendStartingFunds() throws IOException{
        for (int k = 0; k < players.size(); k++) {
            players.get(k).sendIntToTerminationSocket(bank.getPlayerBalance(k));
        }

    }

    private void renewSockets() throws IOException {
        for (Player p : players) {
            serverSocket = new ServerSocket(PortGenerator.PORT.getNewPortNumber());
            p.sendIntToNotificationSocket(PortGenerator.PORT.getPort());
            Socket socket = serverSocket.accept();
            p.renewPrivateSocket(socket);
            serverSocket.close();
            serverSocket = new ServerSocket(PortGenerator.PORT.getNewPortNumber());
            p.sendIntToNotificationSocket(PortGenerator.PORT.getPort());
            socket = serverSocket.accept();
            p.renewPositionSocket(socket);
            serverSocket.close();
            serverSocket = new ServerSocket(PortGenerator.PORT.getNewPortNumber());
            p.sendIntToNotificationSocket(PortGenerator.PORT.getPort());
            socket = serverSocket.accept();
            p.renewNotificationSocket(socket);
            serverSocket.close();
            serverSocket = new ServerSocket(PortGenerator.PORT.getNewPortNumber());
            p.sendIntToNotificationSocket(PortGenerator.PORT.getPort());
            socket = serverSocket.accept();
            p.renewNumberOfPlayersSocket(socket);
            serverSocket.close();
            serverSocket = new ServerSocket(PortGenerator.PORT.getNewPortNumber());
            p.sendIntToNotificationSocket(PortGenerator.PORT.getPort());
            socket = serverSocket.accept();
            p.renewTerminationSocket(socket);
            serverSocket.close();


        }
    }

    private void sendPlayerNumbersAndTokens() throws IOException {
        for (int i = 0; i < players.size(); i++) {
            playersPositionOnBoard[i] = 0;
            tokensName.add(players.get(i).getToken());
        }

        //send number of players and their tokens
        for (Player p : players) {
            p.sendIntToPositionSocket(players.size());
            p.sendObjectToPrivateSocket(tokensName);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void movePlayerTo(int property) throws IOException, InterruptedException {
        newPosition = property;
        sendNewPosition(newPosition, bank.getPropertyStatus(newPosition));
    }

    public int getDicesValue() {
        return playerMove;
    }


    private class Auction implements Runnable, ConstNames {
        private Player player;
        public Auction(Player player) {
            this.player = player;
        }
        @Override
        public void run() {
            try {
                while (true) {
                    int offer = player.receiveIntFromPrivateSocket();
                    if (offer == PASS) {
                        player.sendIntToTerminationSocket(PASS);
                        break;
                    }
                    if (bank.getPlayerBalance(player.getPlayerID()) < offer) {
                        player.sendIntToPrivateSocket(NO_ENOUGH_MONEY);
                    }
                    else{
                        submitOffer(player, offer);
                        for (Player p : players) {
                            p.sendIntToTerminationSocket(auctionOffer);
                        }
                    }
                    //give a good time , joined thread
                    Thread.sleep(500);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void submitOffer(Player player, int offer) {
        if (auctionOffer < offer) {
            auctionPlayer = player;
            auctionOffer = offer;
        }
    }
}