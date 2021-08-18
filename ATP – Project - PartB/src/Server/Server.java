package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.BreadthFirstSearch;

import java.io.*;
import java.net.*;
import java.util.Properties;
import java.util.concurrent.*;

public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        /*
        File file = new File("resources/config.properties");
        Properties properties = new Properties();
        */
        try {
            // Configurations
            int numberOfThreads = 2;

            /*
            if(file.length() == 0){
                Server.Configurations.configurations();
                numberOfThreads = 2;
            }
            else {
                InputStream inputStream = new FileInputStream("resources/config.properties");
                properties.load(inputStream);
                numberOfThreads = Integer.parseInt(properties.getProperty("NumberOfThreads"));
            }
             */

            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Thread thread = new Thread(() -> {
                        handleClient(clientSocket);
                    });
                    pool.execute(thread);
                }
                catch (SocketTimeoutException e) {
                    e.getStackTrace();
                }
            }
            serverSocket.close();
            pool.shutdown();
        }
        catch (IOException e) {
            e.getStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            serverStrategy.ServerStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        }
        catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }

    /*
    public static class Configurations {

        public static void configurations() throws IOException {
            OutputStream out = null;
            try (InputStream input = Server.class.getClassLoader().getResourceAsStream("resources/config.properties")) {
                out = new FileOutputStream("resources/config.properties");

                Properties prop = new Properties();
                if (input == null) {
                    // Set key and value
                    prop.setProperty("TypeMaze", "MyMazeGenerator");
                    prop.setProperty("SolvingMethod", "BreadthFirstSearch");
                    prop.setProperty("NumberOfThreads", "2");

                    // save a properties file
                    prop.store(out, null);
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    */
}