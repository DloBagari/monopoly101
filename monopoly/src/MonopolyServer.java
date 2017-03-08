import java.net.*;
import java.util.*;
import java.io.*;

public class MonopolyServer {
    static int session = 0;
    static ArrayList<Board> existBoards = new ArrayList<>();
    //listen of player how has not need decision yet (join a board or create new board
    private static ArrayList<Player> walkInServer = new ArrayList<>();
    private static ServerSocket serverSocket;

    public static void main (String[] args) {
        try {
            //server socket listen on port 8000
            serverSocket = new ServerSocket(PortGenerator.PORT.getStartPort());
            //send to each player in walkInServer the number of exist boards
            //thread 1 (time: 1000 ms)
            sendNumberOfExistBoards();
            while (true) {
                Player player = initPlayer();
                walkInServer.add(player);
                System.out.println("player " + player.getPlayerAddress()+ "is connected");
                //listen and process the client request , create board or join an exist board
                //thread 2 (time: ~200 ms)
                processRequest(player);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void processRequest(Player player) {
        new Thread(() ->{
            try{
                int request = player.receiveIntFromPrivateSocket();
                //request to join an exist Board
                if (request == 201) {
                    System.out.println("request to join a game");
                    //remove player from walkInServer list
                    removePlayerFromWalkInServer(player);
                    //sleep to give time to other threads
                    Thread.sleep(100);
                    String joinedBoard = player.receiveStringFromPrivateSocket();
                    int boardIndex = Integer.parseInt(joinedBoard.
                            substring(6,joinedBoard.length())) - 1;
                    Board board = existBoards.get(boardIndex);
                    synchronized (existBoards) {
                        board.addPlayer(player);
                        player.setPlayerID(board.size() - 1);
                    }
                    player.sendIntToPrivateSocket(202);
                    player.sendIntToPrivateSocket(board.size() - 1);
                    String token = player.receiveStringFromPrivateSocket();
                    player.setToken(token);
                    board.removeToken(token);
                    //Thread.sleep(100);
                    //player.sendIntToPrivateSocket(board.size() - 1);
                    player.goToNextStage();

                } else if (request == 101) {
                    removePlayerFromWalkInServer(player);
                    Board board = new Board();
                    board.start();
                    existBoards.add(board);
                    //Thread.sleep(10);
                    synchronized (existBoards) {
                        board.addPlayer(player);
                        player.setPlayerID(board.size() - 1);
                    }
                    player.sendIntToPrivateSocket(102);
                    player.sendIntToPrivateSocket(board.size() - 1);
                    String token = player.receiveStringFromPrivateSocket();
                    player.setToken(token);
                    board.removeToken(token);
                    //player.sendIntToPrivateSocket(board.size() - 1);
                    player.goToNextStage();
                    increaseSession();



                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            }).start();
    }

    private static void removePlayerFromWalkInServer(Player player) {
        synchronized (walkInServer) {
            player.goToNextStage();
            walkInServer.remove(player);
        }
    }

    private static synchronized void increaseSession() {
        session++;
    }

    /**
     * send number of exist boards to each player how has not make decision
     * using positionSocket
     * thread 1 (sleeping time: 1000 ms)
     */
    private static void sendNumberOfExistBoards() {
        new Thread(() ->{
            try {
                while (true) {
                    synchronized (walkInServer) {
                        for (Player p : walkInServer) {
                            if( p.getGameStage() == 0)
                                p.SendIntToPositionSocket(session);

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

    private static Player initPlayer() throws IOException {
        Socket temporarySocket = serverSocket.accept();
        //create socket to listen to request for private socket
        ServerSocket privateSocketListener = new ServerSocket(PortGenerator.PORT
                .getNewPortNumber());
        //send port number to client
        //--1--
        sendNewPort(temporarySocket);
        //create a private socket for this client
        Socket privateSocket  = privateSocketListener.accept();
        //close the private socket listener
        privateSocketListener.close();

        //create a listener socket for positionSocket
        ServerSocket positionSocketListener =
                new ServerSocket(PortGenerator.PORT.getNewPortNumber());
        //send port number to client
        //--2--
        sendNewPort(temporarySocket);
        Socket positionSocket = positionSocketListener.accept();
        positionSocketListener.close();

        //create a listener socket for notificationSocket
        ServerSocket notificationSocketListener =
                new ServerSocket(PortGenerator.PORT.getNewPortNumber());
        sendNewPort(temporarySocket);
        Socket notificationSocket = notificationSocketListener.accept();
        notificationSocketListener.close();

        //
        //create a listener socket for positionSocket
        ServerSocket numberOfPlayersSocketListener =
                new ServerSocket(PortGenerator.PORT.getNewPortNumber());
        //send port number to client
        //--2--
        sendNewPort(temporarySocket);
        Socket numberOfPlayersSocket = numberOfPlayersSocketListener.accept();
        positionSocketListener.close();
        temporarySocket.close();
        return new Player(privateSocket, positionSocket, notificationSocket,numberOfPlayersSocket );
    }

    private static void sendNewPort(Socket temporarySocket) throws IOException {
        new DataOutputStream(temporarySocket.getOutputStream())
                .writeInt(PortGenerator.PORT.getPort());
    }


}
