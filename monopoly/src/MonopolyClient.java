import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class MonopolyClient  extends Application implements ConstNames{
    private Socket privateSocket;
    private Socket positionSocket;
    private Socket notificationSocket;
    private Socket numberOfPlayerSocket;
    private Socket terminationSocket;
    private FlowPane existBoardsPane;
    private DataInputStream receiveDataFromPositionSocket;
    private ObjectInputStream receiveObjectFromPosition;
    private ObjectInputStream receiveObjectFromPrivateSocket;
    private DataOutputStream sendDataToPrivateSocket;
    private DataInputStream receiveDataFromPrivateSocket;
    private DataOutputStream sendDataToNotificationSocket;
    private DataInputStream receiveDataFromNotificationSocket;
    private ObjectInputStream receiveObjectFromNotificationSocket;
    private DataInputStream receiveDataFromNumberOfPlayerSocket;
    private DataInputStream  receiveDataByTerminationSocket;
    private DataOutputStream sendDataToTerminationSocket;
    private ObjectInputStream receiveObjectFromTerminationSocket;
    private BorderPane mainPane;
    private VBox userInterface;
    private int gameStage = 0;
    private int playerNumber;
    private int numberOfPlayers;
    private boolean boardOn = false;
    private Text displayNumberOfPlayers;
    private BorderPane boardPane;
    private ImageView token;
    private HBox bottomRow = new HBox();
    private HBox topRow = new HBox();
    private VBox rightCol = new VBox();
    private VBox leftCol = new VBox();
    private BorderPane decisionPane;
    private ArrayList<Cell> cells = new ArrayList<>();
    private ArrayList<ImageView> playersImage = new ArrayList<>();
    private ArrayList<ImageView> playersImage2 = new ArrayList<>();
    private Stage stage;
    private Label howIsTurn;
    private Dice dice;
    private boolean waiting = true;
    private boolean newBoardCreater;
    private Pane boardCenter;
    private Label moneyLabel;
    private int amountMoney;
    private String host = "localhost";
    private boolean myTurn;
    private  int whoISPlaying;
    private ImageView communityCard;
    private ImageView chanceCard;
    private BorderPane communityCardDisplay;
    private BorderPane chanceCardDisplay;
    private TextArea eventDisplay;
    private ScrollPane scrollPane;
    private boolean requestIsDone = true;
    private FlashMoney flashMoneyRed;
    private FlashMoney flashMoneyGreen;
    private boolean gameStarted;
    private boolean tokenIsSelected;


    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        stage = primaryStage;
        initSockets();
        communityCard = new ImageView(new Image("/images/communityCards/c0.png"));
        chanceCard = new ImageView(new Image("/images/communityCards/c0.png"));
        boardCenter = new Pane();
        existBoardsPane = new FlowPane();
        existBoardsPane.setVgap(30);
        existBoardsPane.setVgap(30);
        existBoardsPane.setPrefSize(500, 400);
        mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color:#5499C7;");
        decisionPane = new BorderPane();
        mainPane.setCenter(decisionPane);
        ///delete this
        mainPane.setTop(new Label("Join an Exist Board:"));
        decisionPane.setCenter(existBoardsPane);
        userInterface = new VBox();
        userInterface.setSpacing(10);
        userInterface.setAlignment(Pos.TOP_CENTER);
        userInterface.setMinWidth(362);
        userInterface.setMaxWidth(400);
        displayNumberOfPlayers = new Text("");
        displayNumberOfPlayers.setFont(Font.font(16));
        for (int i = 0; i <10; i++) {
            userInterface.getChildren().add(new EmptySpace());
        }
        userInterface.getChildren().set(0, displayNumberOfPlayers);
        mainPane.setRight(userInterface);
        boardPane = new BorderPane();
        boardPane.setMinWidth(995);
        boardPane.setPrefWidth(995);
        boardPane.setMaxWidth(995);
        boardPane.setMinHeight(965);
        boardPane.setMaxHeight(965);
        boardPane.setPrefHeight(965);
        boardPane.setStyle("-fx-border-color:#F0B27A;-fx-border-width:5;" +
                "-fx-background-color:black");
        Button createBoard = new Button("Create a new Board");
        createBoard.setOnAction(e -> {
            createNewBoard();
        });
        decisionPane.setRight(createBoard);
        howIsTurn= new Label();
        howIsTurn.setFont(Font.font("Copperplate", 14));
        howIsTurn.setContentDisplay(ContentDisplay.RIGHT);
        moneyLabel = new Label();
        moneyLabel.setFont(Font.font("Copperplate", FontWeight.BOLD, 20));
        flashMoneyRed = new FlashMoney(Color.RED);
        flashMoneyGreen = new FlashMoney(Color.GREEN);
        dice = new Dice(this);
        //community card
        communityCardDisplay = new BorderPane();
        communityCardDisplay.setCenter(communityCard);
        Button communityButton = new Button("get a Card");
        communityButton.setOnAction(e -> {
            try {
                onCommunityButtonClick();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        StackPane buttonWrapper = new StackPane();
        buttonWrapper.getChildren().add(communityButton);
        communityCardDisplay.setBottom(buttonWrapper);
        communityCardDisplay.setLayoutX(270);
        communityCardDisplay.setLayoutY(19);
        communityCardDisplay.setVisible(false);
        //chance card
        chanceCardDisplay = new BorderPane();
        chanceCardDisplay.setCenter(chanceCard);
        Button chanceButton = new Button("get the Card");
        chanceButton.setOnAction(e -> {
            try {
                onChanceButtonClick();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        StackPane buttonWrapper2 = new StackPane();
        buttonWrapper2.getChildren().add(chanceButton);
        chanceCardDisplay.setTop(buttonWrapper2);
        chanceCardDisplay.setLayoutX(265);
        chanceCardDisplay.setLayoutY(530);
        chanceCardDisplay.setVisible(false);
        ///
        eventDisplay = new TextArea();
        eventDisplay.setEditable(false);
        scrollPane = new ScrollPane(eventDisplay);
        scrollPane.setMaxWidth(390);
        receiveNumberOfExistBoards();
        //thread listenToGameStart on server , listen to notificationSocket
        listenToGameStartedNotification();
        Scene scene = new Scene(mainPane,1388, 965);
        primaryStage.setTitle("Monopoly");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void stopProgram() throws IOException {
        positionSocket.close();
        numberOfPlayerSocket.close();
        notificationSocket.close();
        privateSocket.close();
        terminationSocket.close();
        stage.close();
    }

    public void setRequestIsDone(boolean value) {
        requestIsDone = value;
    }


    private void onChanceButtonClick() throws IOException {
        sendDataToTerminationSocket.writeInt(ACCEPTED);
        chanceCardDisplay.setVisible(false);
    }

    private void onCommunityButtonClick() throws IOException {
        sendDataToPrivateSocket.writeInt(ACCEPTED);
        communityCardDisplay.setVisible(false);
    }


    private void listenToGameStartedNotification() {
        new Thread (() -> {
            try {

                while (true) {
                    String isStart = receiveDataFromNotificationSocket.readUTF();
                    if (isStart.equals("gameStarted")) {
                        gameStarted = true;
                        gameStage++;
                        if(playerNumber == 0) {

                            Platform.runLater(() -> userInterface.getChildren().remove(1));
                        }

                        Platform.runLater(() -> userInterface.getChildren().set(4, dice));
                        new Thread(new GameController()).start();
                        break;
                    }
                    Thread.sleep(100);
                }
            }catch ( SocketException ex){
                System.err.println("Game is over for you");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private void createNewBoard() {
        try {
            gameStage++;
            sendDataToPrivateSocket.writeInt(101);
            //Thread.sleep(100);
            int response = receiveDataFromPrivateSocket.readInt();
            //Thread.sleep(100);
            if (response == 102)
                newBoardCreater = true;
            playerNumber = receiveDataFromPrivateSocket.readInt();
            while(playerNumber > 7){
                playerNumber = receiveDataFromPrivateSocket.readInt();
            }
            selectToken();

        } catch (IOException ex) {
        ex.printStackTrace();
    //} catch (InterruptedException ex) {
      //  ex.printStackTrace();
    }

    }

    private void selectedBoard(String board) {
        try {
            gameStage++;
            //Thread.sleep(100);// to terminate thread 1 available existBoards listener
            sendDataToPrivateSocket.writeInt(201);
            //sleep to terminate thread 1
            //Thread.sleep(100);
            sendDataToPrivateSocket.writeUTF(board);
            int response = receiveDataFromPrivateSocket.readInt();
            while (response != 202)
                response = receiveDataFromPrivateSocket.readInt();
            playerNumber = receiveDataFromPrivateSocket.readInt();
            while(playerNumber > 7){
                playerNumber = receiveDataFromPrivateSocket.readInt();
            }
            selectToken();
        } catch (IOException ex) {
            ex.printStackTrace();
        //} catch (InterruptedException ex) {
        //    ex.printStackTrace();
        }


    }

    private void initSockets() throws IOException {
        Socket temporarySocket = new Socket(host, PortGenerator.PORT.getStartPort());
        //receive new port number for this client to connect to its private socket on server
        //--1--
        int port = new DataInputStream(temporarySocket.getInputStream()).readInt();
        privateSocket = new Socket(host, port);
        //receive new port number for this client to connect to its position socket on server
        //--2--
        port = new DataInputStream(temporarySocket.getInputStream()).readInt();
        positionSocket = new Socket(host, port);
        //receive new port number for this client to connect to its notification socket on server
        //--3--
        port = new DataInputStream(temporarySocket.getInputStream()).readInt();
        notificationSocket = new Socket(host, port);

        port = new DataInputStream(temporarySocket.getInputStream()).readInt();
        numberOfPlayerSocket = new Socket(host, port);
        initPrivateSocket();
        initPositionSocket();
        initNotificationSocket();
        initNumberOfPlayersSocket();

    }
    private void initPrivateSocket() throws IOException {
        receiveObjectFromPrivateSocket =
                new ObjectInputStream(privateSocket.getInputStream());
        sendDataToPrivateSocket = new DataOutputStream(privateSocket.getOutputStream());
        receiveDataFromPrivateSocket =
                new DataInputStream(privateSocket.getInputStream());

    }

    private void initNotificationSocket() throws IOException {
        sendDataToNotificationSocket =
                new DataOutputStream(notificationSocket.getOutputStream());
        receiveDataFromNotificationSocket =
                new DataInputStream((notificationSocket.getInputStream()));
        receiveObjectFromNotificationSocket = new ObjectInputStream(notificationSocket.getInputStream());
    }

    private void initPositionSocket() throws IOException {
        receiveDataFromPositionSocket =
                new DataInputStream(positionSocket.getInputStream());
        receiveObjectFromPosition = new ObjectInputStream(positionSocket.getInputStream());
    }

    private void initNumberOfPlayersSocket() throws IOException {
        receiveDataFromNumberOfPlayerSocket = new DataInputStream(numberOfPlayerSocket.getInputStream());
    }


    /**
     * listen to number of exist boards
     * using positionSocket
     * thread 1 (sleeping time: 1000 ms)
     */
    private void receiveNumberOfExistBoards() {
        new Thread(() -> {
            try {
                int availableBoards;
                while (gameStage == 0) {
                    availableBoards= receiveDataFromPositionSocket.readInt();
                    if (existBoardsPane.getChildren().size() != availableBoards) {
                        Platform.runLater(() -> existBoardsPane.getChildren().clear());
                        for (int i = 0; i < availableBoards; i++) {
                            Button bt = new Button("Board_" + (i + 1));
                            bt.setOnAction(e -> selectedBoard(bt.getText()));
                            Platform.runLater(() -> existBoardsPane.getChildren().add(bt));
                        }
                    }
                    Thread.sleep(500);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }).start();

    }



    /**
     * get list of available token from server and create tokens
     * using positionSocket
     * thread_3 tokens (sleeping time: 1500)
     * @throws IOException
     */
    private void selectToken() throws IOException {
        Platform.runLater(() ->existBoardsPane.getChildren().clear());
        Text text = new Text("\nSelect your Token:\n\n");
        text.setFont(Font.font("Copperplate", 18));
        text.setUnderline(true);
        mainPane.setTop(text);
        decisionPane.setRight(null);
        //free the buffer of input data for position Socket
        while(receiveDataFromPositionSocket.available() > 1) {
            receiveDataFromPositionSocket.readByte();
        }


        new Thread(() -> {
            try {
                ArrayList<String> currentTokens = new ArrayList<>();
                ArrayList<String> availableTokens = new ArrayList<>();
                int newNumberOfPlayers = numberOfPlayers;
                while (gameStage == 2 || gameStage == 1) {
                    try {
                        if (!gameStarted)
                        newNumberOfPlayers = receiveDataFromNumberOfPlayerSocket.readInt();
                    }catch (SocketException ex){
                        break;
                    }
                    if (gameStage == 1){
                        availableTokens = (ArrayList<String>)receiveObjectFromPrivateSocket.readObject();
                    }
                    if (currentTokens.size() != availableTokens.size() && !tokenIsSelected) {
                        currentTokens = availableTokens;
                        Platform.runLater(() -> existBoardsPane.getChildren().clear());
                        for (String s : availableTokens) {
                            Image img = new Image("/images/" + s + ".png");
                            ImageView imageView = new ImageView(img);
                            imageView.setOnMouseClicked(e -> OnTokenClick(e, s));
                            Platform.runLater(() -> existBoardsPane.getChildren().add(imageView));
                        }
                    }
                    if (numberOfPlayers != newNumberOfPlayers) {
                        numberOfPlayers = newNumberOfPlayers;
                        Platform.runLater(() ->
                            displayNumberOfPlayers.setText("Number of players: " + numberOfPlayers)
                        );
                    }
                    Thread.sleep(500);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }catch(ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }).start();

    }

    private void OnTokenClick(MouseEvent e, String s) {
        try {
            tokenIsSelected = true;
            gameStage++;
            setBoard();
            //time needed to load  the board , send creation of new board to server
            //Thread.sleep(100);
            //send selected token to server
            sendDataToPrivateSocket.writeUTF(s);
            if(newBoardCreater) {
                Button startGame = new Button("Start the Game");
                startGame.setOnAction(event -> {
                    try {
                        //if board initialized
                        if (boardOn) {
                            startTheGame();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                userInterface.getChildren().set(1, startGame);
            }
            Label playerToken = new Label("Your token ");
            playerToken.setFont(Font.font("Copperplate", 14));
            playerToken.setContentDisplay(ContentDisplay.RIGHT);
            token = new ImageView(new Image("/images/" + s +".png"));
            playerToken.setGraphic(token);
            userInterface.getChildren().set(2, playerToken);
            userInterface.getChildren().set(3, howIsTurn);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void startTheGame() throws IOException {
        if (numberOfPlayers > 1)
        sendDataToNotificationSocket.writeUTF("StartGame");
    }

    private void setBoard() {
        mainPane.setCenter(boardPane);
        mainPane.setTop(null);
        initBoard();
        boardPane.setTop(topRow);
        boardPane.setBottom(bottomRow);
        boardPane.setRight(rightCol);
        boardPane.setLeft(leftCol);
        boardOn = true;
        //stage.setResizable(false);
    }

    private void initBoard() {
        boardCenter.getChildren().add(new Cell(this,new ImageView(new Image("/images/bb.png")), 0, playerNumber));
        boardCenter.getChildren().add(communityCardDisplay);
        boardCenter.getChildren().add(chanceCardDisplay);
        boardPane.setCenter(boardCenter);
        cells.add(new Cell(this,new ImageView(new Image("/images/bt1.png")), 1, playerNumber));
        for (int i = 1; i<12; i++) {
            Image image = new Image("/images/bt" + i + ".png");
            ImageView imageView = new ImageView(image);
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            Cell cell = new Cell(this, imageView, i, playerNumber);
            bottomRow.getChildren().add(cell);
            cells.add(0 ,cell);

        }
        InitWhiteBoard.initWhiteBoardsBottom(cells, boardCenter, bottomRow);
        for (int i = 1; i<10; i++) {
            Image image2 = new Image("/images/lf" + i + ".png");
            ImageView imageView = new ImageView(image2);
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            Cell cell = new Cell(this, imageView, i, playerNumber);
            leftCol.getChildren().add(cell);
            cells.add(11, cell);
        }
        cells.remove(20);
        InitWhiteBoard.initWhiteBoardLeft(cells, boardCenter, leftCol);
        for (int i = 1; i<12; i++) {

            Image image2 = new Image("/images/t" + i + ".png");
            ImageView imageView = new ImageView(image2);
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            Cell cell = new Cell(this, imageView, i, playerNumber);
            topRow.getChildren().add(cell);
            cells.add(cell);
        }
        InitWhiteBoard.initWhiteBoardTop(cells, boardCenter, topRow );
        for (int i = 1; i<10; i++) {
            Image image = new Image("/images/r" + i + ".png");
            ImageView imageView = new ImageView(image);
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            Cell cell = new Cell(this, imageView, i, playerNumber);
            rightCol.getChildren().add(cell);
            cells.add(cell);
        }

        InitWhiteBoard.initWhiteBoardsRight(cells, boardCenter, rightCol);

        for (int i = 0; i < cells.size(); i++) {
            cells.get(i).setCellNumber(i);
        }

    }

    public void setWaiting(boolean b) {
        waiting = b;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void sendIntToPrivateSocket(int value)throws IOException {
        sendDataToPrivateSocket.writeInt(value);
    }


    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void addToUserInterface(Node payRentPane,int position) throws InterruptedException {
        userInterface.getChildren().set(position, payRentPane);
        Thread.sleep(100);
    }

    public void sendStringToPrivateSocket(String id) throws IOException {
        sendDataToPrivateSocket.writeUTF(id);
    }

    public String receiveStringFromPrivateSocket() throws IOException {
        return receiveDataFromPrivateSocket.readUTF();
    }

    public int receiveIntFromPrivateSocket() throws IOException {
        return receiveDataFromPrivateSocket.readInt();
    }

    public void updateCommunityCard(int cardID) throws InterruptedException {
        communityCard.setImage(new Image("/images/communityCards/" + cardID + ".png"));
        communityCardDisplay.setVisible(true);
        Thread.sleep(200);
    }

    public void updateChanceCard(int cardID) throws InterruptedException {
        chanceCard.setImage(new Image("/images/chanceCards/" + cardID + ".png"));
        Thread.sleep(200);
    }

    public void setChanceCardDisplayerVisible(boolean value) throws InterruptedException {
        chanceCardDisplay.setVisible(value);
        Thread.sleep(100);
    }

    public int receiveIntFromNotificationSocket() throws IOException {
        return receiveDataFromNotificationSocket.readInt();
    }

    public List<Integer> receiveObjectFromPrivateSocket() throws IOException, ClassNotFoundException {
        return (List<Integer>) receiveObjectFromPrivateSocket.readObject();
    }

    public int receiveIntFromTerminationSocket() throws IOException {
        return receiveDataByTerminationSocket.readInt();
    }

    public ArrayList<Integer> receiveObjectFromterminationSocket() throws IOException, ClassNotFoundException {
        return (ArrayList<Integer>) receiveObjectFromTerminationSocket.readObject();
    }

    public void setMortgage(List<Integer> properties) {
        for (Integer i : properties) {
            Button mortgage = new Button("Mortgage\nthis property");
            mortgage.setFont(Font.font(10));
            mortgage.setOnAction(e -> {
                try {
                    undoMortgage(properties, i);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            cells.get(i).getChildren().add(mortgage);
        }
    }

    private void undoMortgage(List<Integer> properties, Integer propertyID) throws IOException {
        sendIntToPrivateSocket(propertyID);
        for (Integer i : properties) {
            cells.get(i).getChildren().remove(cells.get(i).getChildren().size() - 1);
        }
        cells.get(propertyID).setMortgageed();

    }

    public void removePlayer(int loser) throws IOException {
        for (Cell cell : cells) {
            cell.removeTokenFromTokenQueue(loser);
        }
        displayNumberOfPlayers.setText("Number of players: " + --numberOfPlayers);
        if (loser == playerNumber)
            stopProgram();

    }

    public int getMoneyAmount() {
        return  amountMoney;
    }

    private class GameController implements Runnable {
        private List<Integer> playersCurrentPosition = new ArrayList<>();


        @Override
        public void run() {
            try {
                //Platform.runLater(() -> stage.setResizable(false));
                for (int i = 0; i < numberOfPlayers; i++) {
                    playersCurrentPosition.add(0);
                }
                for (Cell cell : cells) {
                    for (int i = 0; i < numberOfPlayers; i++) {
                        Platform.runLater(() -> {
                            cell.addTokenTotokensQueue(new EmptySpace());
                        });
                    }
                }
                Thread.sleep(500);
                renewSockets();
                Thread.sleep(500);
                numberOfPlayers = receiveDataFromPositionSocket.readInt();
                Platform.runLater(() ->
                        displayNumberOfPlayers.setText("Number Of Players: " + numberOfPlayers));
                ArrayList<String> tokensName =
                        (ArrayList<String>)receiveObjectFromPrivateSocket.readObject();
                for (String s : tokensName) {
                    playersImage.add(new PrefImageView(new Image("/images/" + s + ".png"), cells.get(0)));
                    playersImage2.add(new ImageView(new Image("/images/" + s + ".png")));
                }
                Thread.sleep(1000);
                receiveStartingFunds();
                Platform.runLater(() ->
                        userInterface.getChildren().set(userInterface.getChildren().size() -1, scrollPane));
                Thread.sleep(100);
                //thread to listen to statements on numberOfPlayersSocket
                new Thread (() ->{
                    try {
                        //free Number of player socket , just to ensure buffer is free before we use that socket
                        int a = receiveDataFromNumberOfPlayerSocket.readInt();
                        while (a != -1) {
                            a = receiveDataFromNumberOfPlayerSocket.readInt();
                            Thread.sleep(10);
                        }
                        while (true) {
                            String statement = receiveDataFromNumberOfPlayerSocket.readUTF();
                            eventDisplay.appendText(statement);
                            Thread.sleep(500);
                        }
                    } catch (IOException e) {
                        System.out.println("game is Over for player " + playerNumber);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                //thread to listen to new Positions on positionSocket
                new Thread(() -> {
                    int newPositions;
                    int status;
                    try{
                        while (true) {
                            status = receiveDataFromPositionSocket.readInt();
                            whoISPlaying = receiveDataFromPositionSocket.readInt();
                            newPositions = receiveDataFromPositionSocket.readInt();
                            updatePositions(playerNumber, whoISPlaying, newPositions, status);
                            Thread.sleep(500);
                        }
                    } catch (IOException e) {
                        System.out.println("game is Over for player " + playerNumber);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                //thread to listen to amount of money on notificationSocket()
                new Thread(() -> {
                    try {
                        while (true) {
                            int amount = receiveIntFromNotificationSocket();
                            Platform.runLater(() -> moneyLabel.setText(amount + ""));
                            if (amountMoney != amount) {
                                try {
                                    if (amountMoney > amount)
                                        flashMoneyRed.play();
                                    else if (amountMoney < amount)
                                        flashMoneyGreen.play();

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                amountMoney = amount;

                            }
                            Thread.sleep(1000);
                        }
                        } catch(IOException e){
                            System.out.println("game is Over for player " + playerNumber);
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }

                }).start();

                while (true) {
                    //just to free  buffer of Private socket for security
                    String freeBuffer = receiveDataFromPrivateSocket.readUTF();
                    while (!freeBuffer.equals("FREE"))
                        freeBuffer = receiveDataFromPrivateSocket.readUTF();
                    String notification = receiveDataFromPrivateSocket.readUTF();
                    int player = Integer.parseInt(notification.substring(7, 8)) - 1;
                    if(notification.equals("Player " + (playerNumber + 1) +" Turn")) {
                        myTurn = true;
                        //dice.setActive(true);

                        Platform.runLater(() -> {
                            howIsTurn.setText("Your Turn");
                            howIsTurn.setGraphic(playersImage2.get(playerNumber));
                            dice.setActive();
                        });
                        Thread.sleep(200);
                        sendMove();
                    }else {
                        //no requests here just update whoIsTurn's token
                        Platform.runLater(() -> {
                            howIsTurn.setText(notification);
                            howIsTurn.setGraphic(playersImage2.get(player));
                        });
                    }
                    //wait if player has an advance card to be played
                    Thread.sleep(200);
                    waitingToFinishingRequest();
                    Thread.sleep(1500);

                }
            } catch (IOException ex) {
                System.out.println("game is over for player " + playerNumber);
           } catch (ClassNotFoundException ex) {
               ex.printStackTrace();
           } catch (InterruptedException ex) {
               ex.printStackTrace();
           }
        }


        private void waitingToFinishingRequest() throws InterruptedException {
            while (requestIsDone) {
                Thread.sleep(100);
            }
            requestIsDone = true;
        }

        private void receiveStartingFunds() throws IOException {
            amountMoney = receiveDataByTerminationSocket.readInt();
            StackPane pane = new StackPane();
            pane.getChildren().add(new ImageView("/images/money.png"));
            moneyLabel.setText(amountMoney + "");
            pane.getChildren().addAll(moneyLabel ,flashMoneyRed, flashMoneyGreen);
            Platform.runLater(() -> userInterface.getChildren().set(5, pane));
        }

        private void renewSockets() throws IOException{
            int port = receiveDataFromNotificationSocket.readInt();
            privateSocket.close();
            privateSocket = new Socket(host, port);
            initPrivateSocket();
            port = receiveDataFromNotificationSocket.readInt();
            positionSocket.close();
            positionSocket = new Socket(host, port);
            initPositionSocket();
            port = receiveDataFromNotificationSocket.readInt();
            notificationSocket.close();
            notificationSocket = new Socket(host, port);
            initNotificationSocket();
            port = receiveDataFromNotificationSocket.readInt();
            numberOfPlayerSocket.close();
            numberOfPlayerSocket = new Socket(host, port);
            initNumberOfPlayersSocket();
            port = receiveDataFromNotificationSocket.readInt();
            terminationSocket = new Socket(host, port);
            receiveDataByTerminationSocket = new DataInputStream(terminationSocket.getInputStream());
            receiveObjectFromTerminationSocket = new ObjectInputStream(terminationSocket.getInputStream());
            sendDataToTerminationSocket = new DataOutputStream(terminationSocket.getOutputStream());
        }

        private void sendMove() throws IOException, InterruptedException {
            waitForMove();
                sendDataToPrivateSocket.writeInt((dice.getNumber()));
        }

        private void waitForMove() throws InterruptedException {
            while (waiting) {
                Thread.sleep(100);
            }
            waiting = true;
        }

        private void updatePositions(int whoIsTurn, int player, int  newPositions, int status) {
            try {
                Platform.runLater(() -> {
                    //cell.removeTokenFromTokenQueue(targetPane);
                    Cell toCell = cells.get(newPositions);
                    PrefImageView a = (PrefImageView) playersImage.get(player);
                    try {
                        a.updatePosition(toCell, whoIsTurn, player, status);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    //cell.addTokenTotokensQueue(a, targetPane);
                });
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args ) {
        launch();
    }
}

