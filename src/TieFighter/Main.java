package TieFighter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

    }

    private WeightedGraph readGalaxy(File inputFile) throws FileNotFoundException {
        try {
            Scanner input = new Scanner(inputFile);

            WeightedGraph weightedGraph = new WeightedGraph();
            while(input.hasNextLine()) {
                String line = input.nextLine();
                String[] splitLine = line.split(" ");

                Integer v = Integer.parseInt(splitLine[0]);
                weightedGraph.addVertex(v);

                for(int i = 1; i < splitLine.length; i++) {
                    String[] weightedEdge = splitLine[i].split(",");
                    int u = Integer.parseInt(weightedEdge[0]);
                    double weight = Double.parseDouble(weightedEdge[1]);
                    weightedGraph.addEdge(u, v, weight);
                }
            }

            return weightedGraph;
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Please check filepath.");
        }
        return null;
    }

    private
}
