import java.io.IOException;
import java.util.ArrayList;

public class Board extends Thread {

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<String> availableTokens = new ArrayList<>();
    private boolean gameStarted = false;

    @Override
    public void run() {
        try {
            availableTokens = new ArrayList<>();
            for (int i = 1; i < 9; i++) {
                availableTokens.add("p" + i);
            }
            sendAvailableTokens();
            listenToStartGame();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * sending available tokens to each player how is not select a token yet
     * using position socket
     * thread_3 tokens (sleeping time: 1500 ms)
     */
    private void sendAvailableTokens() {
        new Thread(() -> {
            try {
                while (!gameStarted) {
                    for (Player p : players) {
                        if (p.getGameStage() == 1) {
                            p.sendIntToNumberOfPlayersSocket(players.size());
                            //Thread.sleep(200);
                            p.sendObjectToPrivateSocket((ArrayList<String>)availableTokens.clone());
                        }
                        else if (p.getGameStage() == 2) {
                            p.sendIntToNumberOfPlayersSocket(players.size());
                        }
                    }
                    Thread.sleep(500);
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public  void listenToStartGame() {
        new Thread(() -> {
            try {
                mainLoop:
                while (true) {
                    if (players.get(0).getGameStage() == 2) {
                        String gameStart = players.get(0).receiveStringFromNotificationSocket();
                        if (gameStart.equals("StartGame")) {
                            gameStarted = true;
                            new Thread(new BoardController(players)).start();
                            MonopolyServer.existBoards.remove(this);
                            MonopolyServer.session--;

                            break mainLoop;
                        }
                    }
                    Thread.sleep(100);
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public synchronized void addPlayer(Player player) {
        players.add(player);
    }

    public void removeToken(String token) {
        synchronized (availableTokens) {
            availableTokens.remove(token);
        }
    }

    public int size() {
        return players.size();
    }
}
