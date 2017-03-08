import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dlo on 02/02/17.
 */
enum  PortGenerator {
    PORT;
    private int newPortNumber = 8000;
    private int  positingPort =7999;
    private int notificationPort = 7998;
    private int startPort = 8000;
    private LinkedList<Integer> availablePorts = new LinkedList<>();

    public synchronized int getNewPortNumber() {
        if (availablePorts.size() > 0) {
            return availablePorts.removeFirst();
        }
        return ++newPortNumber;

    }

    public synchronized void addPort(int port) {
        availablePorts.add(port);
    }

    public synchronized int getPort() {
        return newPortNumber;
    }

    public synchronized int getPositingPort() {
        return positingPort;
    }

    public synchronized  int getStartPort() {
        return startPort;
    }

    public synchronized  int getNotificationPort() {
        return  notificationPort;

    }
}