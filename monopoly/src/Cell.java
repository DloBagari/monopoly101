import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class Cell extends StackPane implements ConstNames {
    private ImageView imageView;
    private BorderPane pane;
    private FlowPane tokensQueue;
    private int numberOftokensInCell;
    private BorderPane whiteBoard;
    private BorderPane diplayBoard;
    private int playerNumber;
    private Button buy;
    private Button auction;
    private int cellNumber;
    private MonopolyClient board;
    private int owner = -1;// -1 : no owner
    private boolean waiting = true;
    private Map<Integer, Integer> prices;
    private ImageView centerImageNoMoney;
    private ImageView centerImageHasMoney;
    private int whoIsTurn;
    private FlowPane payRentPane;
    private Label rentInfo;
    private boolean requestIsDone;
    private Label ownerLabel;
    private int moneyCheck;


    public Cell (MonopolyClient board, ImageView image,int cellNumber, int playerNumber)  {
        initPrice();
        this.board = board;
        ownerLabel = new Label();
        ownerLabel.setStyle("-fx-background-color:white");
        ownerLabel.setFont(Font.font(10));
        payRentPane = new FlowPane(10, 10);
        payRentPane.setStyle("-fx-background-color:#FF717E;");
        payRentPane.setAlignment(Pos.CENTER);
        rentInfo = new Label();
        Button rentButton = new Button("Pay");
        rentButton.setOnAction(e -> {
            try {
                payTheRent();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        payRentPane.getChildren().addAll(rentInfo, rentButton);
        payRentPane.setVisible(false);
        //can be initialized in  initWhiteBoard
        centerImageNoMoney = new ImageView("/images/no_money.jpg");
        this.playerNumber = playerNumber;
        this.cellNumber = cellNumber;
        this.pane = new BorderPane();
        this.whiteBoard = new BorderPane();
        this.imageView = image;
        this.tokensQueue = new FlowPane();
        tokensQueue.setHgap(0);
        tokensQueue.setVgap(0);
        tokensQueue.setPrefWrapLength(100);
        tokensQueue.setAlignment(Pos.CENTER);
        this.pane.setCenter(this.tokensQueue);
        this.setStyle("-fx-border-color:black;");
        //add event listener to width and height
        this.getChildren().addAll(imageView, this.pane);
        buy = new Button("buy");
        buy.setStyle("-fx-border-color:black; -fx-border-width:1;");
        buy.setOnAction(e -> {
            try {
                buyProperty();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        auction = new Button("Auction");
        auction.setStyle("-fx-border-color:black; -fx-border-width:1;");
        auction.setOnAction(e -> {
            try {
                setAuction();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void payTheRent() throws IOException {
        payRentPane.setVisible(false);
        board.sendIntToPrivateSocket(ACCEPTED);
    }

    private void buyProperty() throws IOException, InterruptedException {
        if (moneyCheck < 0) {
            ((BorderPane)whiteBoard.getCenter()).setCenter(centerImageNoMoney);

        }else {
            whiteBoard.setVisible(false);
            this.owner = whoIsTurn;
            setOwnerLabel();
            board.sendIntToPrivateSocket(ACCEPTED);
            board.sendIntToPrivateSocket(cellNumber);
            ((BorderPane)whiteBoard.getCenter()).setCenter(centerImageHasMoney);
        }
    }

    private void setAuction() throws IOException {
        board.sendIntToPrivateSocket(AUCTION);
        board.sendIntToPrivateSocket(cellNumber);
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public Button getbuyButton() {
        return buy;
    }

    public Button getAuctionButton() {
        return auction;
    }

    public BorderPane getWhiteBoard() {
        return  whiteBoard;
    }

    public void setWhiteBoard( BorderPane board) {
        whiteBoard = board;
    }
    public synchronized void addTokenTotokensQueue(ImageView token ,int whoIsTurn,  int position, int status)
            throws InterruptedException, IOException, ClassNotFoundException {
        this.whoIsTurn = whoIsTurn;
        requestIsDone = true;
        if(centerImageHasMoney == null)
            centerImageHasMoney = (ImageView)((BorderPane)whiteBoard.getCenter()).getCenter();
        numberOftokensInCell++;
        tokensQueue.getChildren().set(position, token);
        updateSizeOfTokens();
        //you might need to remove this when processing the player turn
        if (status == AUCTION) {
            requestIsDone = false;
            int mortgage = board.receiveIntFromTerminationSocket();
            BorderPane paneCenter = (BorderPane) whiteBoard.getCenter();
            AuctionPane auctionPane = new AuctionPane(board, mortgage);
            if(board.isMyTurn()) {
                auctionPane.setBottom(null);
                board.sendIntToPrivateSocket(PASS);
                auctionPane.setPassed(true);
            }
            whiteBoard.setCenter(auctionPane);
            whiteBoard.setVisible(true);
            Thread.sleep(200);
            new Thread(() -> {
                try{
                    while(true) {
                        int newOffer = board.receiveIntFromTerminationSocket();
                        if (newOffer == PASS) {
                            Platform.runLater(() -> {
                                auctionPane.setBottom(null);
                            });
                        }
                        else if (newOffer == TERMINATE_AUCTION) {
                            int owner = board.receiveIntFromTerminationSocket();
                            if (owner >= 0 && owner <=7) {
                                this.owner = owner;
                                setOwnerLabel();
                            }
                            Platform.runLater(() -> whiteBoard.setCenter(paneCenter));
                            whiteBoard.setVisible(false);
                            board.setRequestIsDone(false);
                            board.setMyTurn(false);
                            break;
                        }
                        else
                            Platform.runLater(() -> {
                                try {
                                    auctionPane.setLastOffer(newOffer);
                                    auctionPane.reset();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        Thread.sleep(300);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        else if (status == LOST) {
            int loser = board.receiveIntFromTerminationSocket();
            board.removePlayer(loser);
        }

        else if (status == NO_ENOUGH_MONEY) {
            requestIsDone = false;
            if (board.isMyTurn()) {
                List<Integer> properties = board.receiveObjectFromterminationSocket();
                board.setMortgage(properties);
                Thread.sleep(100);
            }
        }
        else if (board.isMyTurn() && whoIsTurn == position) {
            int cardID;
            switch (status) {
                case NO_OWNER:case RIALROAD:case UTILITY:
                    moneyCheck = board.receiveIntFromPrivateSocket();
                    whiteBoard.setVisible(true);
                    requestIsDone = false;
                    break;
                case NOT_PROPERTY:
                    whiteBoard.setVisible(false);
                    break;
                case CHANCE_CARD:
                    cardID = board.receiveIntFromTerminationSocket();
                    int cardType = board.receiveIntFromTerminationSocket();
                    if (cardType == MOVE_TO || cardType == PAY_TO_BANK)
                        requestIsDone = false;
                    board.updateChanceCard(cardID);
                    board.setChanceCardDisplayerVisible(true);
                    break;
                case COMM_CHEST:
                    cardID = board.receiveIntFromPrivateSocket();
                    board.updateCommunityCard(cardID);
                    break;
                case INCOME_TAX:
                    rentInfo.setText("Pay Income Tax");
                    payRentPane.setVisible(true);
                    board.addToUserInterface(payRentPane, 6);
                    break;
                case 0:case 1:case 2:case 3:case 4:case 5:case 6:case 7:
                    if (owner == -1) {
                        owner = status;
                        setOwnerLabel();
                    }
                    if (owner != whoIsTurn) {
                        String message = board.receiveStringFromPrivateSocket();
                        rentInfo.setText(message);
                        payRentPane.setVisible(true);
                        board.addToUserInterface(payRentPane, 6);
                        requestIsDone = false;
                        Thread.sleep(100);
                    }
                    else {
                        board.sendIntToPrivateSocket(PASS);
                    }
                    break;
                case LUXURY_TAX:
                    rentInfo.setText("Pay LUXURY TAx");
                    payRentPane.setVisible(true);
                    board.addToUserInterface(payRentPane, 6);
                    break;
                case MORTGAGE:
                    break;

            }
        }
        if (requestIsDone) {
            //stop waiting in board thread
            board.setRequestIsDone(false);
            board.setMyTurn(false);
        }
    }

    private void setOwnerLabel() {
        Platform.runLater(() -> {
            ownerLabel.setText("Owner player " + (owner + 1));
            StackPane ownerLabelWrapper = new StackPane();
            ownerLabelWrapper.getChildren().add(ownerLabel);
            if (cellNumber > 20 && cellNumber < 30)
                pane.setTop(ownerLabelWrapper);
            else
                pane.setBottom(ownerLabelWrapper);
        });
    }

    private void waitForMove() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    private void updateSizeOfTokens() {
        if (numberOftokensInCell >= 1) {
            for (int i = 0; i < tokensQueue.getChildren().size(); i++) {
                if (tokensQueue.getChildren().get(i) instanceof PrefImageView) {
                    ((PrefImageView) tokensQueue.getChildren().get(i)).updateSize();
                }
            }
        }
    }

    public synchronized void removeTokenFromTokenQueue(int position) {
        if (tokensQueue.getChildren().get(position) instanceof  PrefImageView) {
            numberOftokensInCell--;
            tokensQueue.getChildren().set(position, new EmptySpace());
        }
        updateSizeOfTokens();
        whiteBoard.setVisible(false);
    }

    public synchronized int getNumberOftokensInCell() {
        return numberOftokensInCell;
    }

    public synchronized void addTokenTotokensQueue(Node node) {
        tokensQueue.getChildren().add(node);
    }


    public void setCellNumber(int i) {
        cellNumber = i;
    }

    private void initPrice() {
        prices = new HashMap<>();
        prices.put(1, 60);
        prices.put(3, 60);
        prices.put(5, 200);
        prices.put(6, 100);
        prices.put(8, 100);
        prices.put(9, 120);
        prices.put(11, 140);
        prices.put(12, 150);
        prices.put(13, 140);
        prices.put(14, 160);
        prices.put(15, 200);
        prices.put(16, 180);
        prices.put(18, 180);
        prices.put(19, 200);
        prices.put(21, 220);
        prices.put(23, 220);
        prices.put(24, 240);
        prices.put(25, 200);
        prices.put(26, 260);
        prices.put(27, 260);
        prices.put(28, 150);
        prices.put(29, 280);
        prices.put(31, 300);
        prices.put(32, 300);
        prices.put(34, 320);
        prices.put(35, 200);
        prices.put(37, 350);
        prices.put(39, 400);
    }

    public void setMortgageed() {
        ownerLabel.setText("Mortgaged");
        owner = MORTGAGE;
    }
}