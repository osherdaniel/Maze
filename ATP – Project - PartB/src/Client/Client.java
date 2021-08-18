package Client;

import java.io.*;
import java.net.*;

public class Client{
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;


    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer() {
        try {
            Socket theServer = new Socket(serverIP, serverPort);
            clientStrategy.clientStrategy(theServer.getInputStream(), theServer.getOutputStream());
            theServer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
