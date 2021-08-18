package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;
import java.util.Properties;

public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public void ServerStrategy(InputStream inputStream, OutputStream outputStream) {
        /*
        File file = new File("resources/config.properties");
        Properties properties = new Properties();
         */

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);

            toClient.flush();

            int[] mazeSize = (int[]) fromClient.readObject();

            // Configurations
            IMazeGenerator type = new MyMazeGenerator();

            /*
            if(file.length() == 0){
                Server.Configurations.configurations();
                type = new MyMazeGenerator();
            }
            else {
                inputStream = new FileInputStream("resources/config.properties");
                properties.load(inputStream);
                String mazeType = properties.getProperty("TypeMaze");
                if(mazeType == "SimpleMazeGenerator")
                    type = new SimpleMazeGenerator();
                else
                    type = new MyMazeGenerator();
            }
             */

            Maze generatedMaze = type.generate(mazeSize[0], mazeSize[1]);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MyCompressorOutputStream returnValue = new MyCompressorOutputStream(out);

            byte[] mazeToByte = generatedMaze.toByteArray();
            returnValue.write(mazeToByte);
            returnValue.flush();

            toClient.writeObject(out.toByteArray());
            toClient.flush();

            fromClient.close();
            toClient.close();
            out.close();
        }

        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
