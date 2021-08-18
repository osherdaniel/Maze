package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.util.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    private Map<String, String> hashTable;
    private static int solutionIndex = 0;
    private static int mazeIndex = 0;

    public ServerStrategySolveSearchProblem() {
        hashTable = Collections.synchronizedMap(new HashMap <String,String>());
    }

    @Override
    public void ServerStrategy(InputStream inputStream, OutputStream outputStream) {
        synchronized (this) {
        /*
        File file = new File("resources/config.properties");
        Properties properties = new Properties();
        */
            try {
                ObjectInputStream fromClient = new ObjectInputStream(inputStream);
                ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

                toClient.flush();

                Solution returnValue = new Solution();
                Maze myMaze = (Maze) fromClient.readObject();

                String tempDirectoryPath = System.getProperty("java.io.tmpdir");

                // Get the maze searching algorithm
                ISearchingAlgorithm mazeSearchType = new BreadthFirstSearch();
                ;

                // Configurations
                String solvingMethod = "BreadthFirstSearch";

            /*
            String solvingMethod = "";
            if(file.length() == 0)
                Server.Configurations.configurations();
            inputStream = new FileInputStream("resources/config.properties");
            properties.load(inputStream);
            solvingMethod = properties.getProperty("SolvingMethod");
            if(solvingMethod == "BreadthFirstSearch")
                mazeSearchType = new BreadthFirstSearch();
            else if(solvingMethod == "DepthFirstSearch")
                mazeSearchType = new DepthFirstSearch();
            else
                mazeSearchType = new BestFirstSearch();
             */
                File newFile = null;

                if (hashTable.containsKey(myMaze.toString())) {
                    String name = hashTable.get(myMaze.toString());
                    newFile = new File(tempDirectoryPath, name);

                    FileInputStream inputFile = new FileInputStream(newFile);
                    ObjectInputStream returnFile = new ObjectInputStream(inputFile);

                    // Get the file from the solution we got before
                    returnValue = (Solution) returnFile.readObject();
                    returnFile.close();
                } else {
                    // Create the MAZE file
                    String name = String.valueOf(mazeIndex);
                    mazeIndex = mazeIndex + 1;

                    newFile = new File(tempDirectoryPath, "Maze" + name);

                    FileOutputStream outFile = new FileOutputStream(newFile);
                    ObjectOutputStream returnFile = new ObjectOutputStream(outFile);

                    returnFile.writeObject(returnValue);

                    // Create the SOLUTION file
                    name = String.valueOf(solutionIndex);
                    solutionIndex = solutionIndex + 1;

                    newFile = new File(tempDirectoryPath, name);

                    SearchableMaze searchableMaze = new SearchableMaze(myMaze);
                    returnValue = mazeSearchType.solve(searchableMaze);

                    // Create new Solution
                    outFile = new FileOutputStream(newFile);
                    returnFile = new ObjectOutputStream(outFile);

                    hashTable.put(myMaze.toString(), name);

                    returnFile.writeObject(returnValue);
                    returnFile.flush();
                }

                toClient.writeObject(returnValue);
                toClient.flush();

                fromClient.close();
                toClient.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}