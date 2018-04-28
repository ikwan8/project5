package TieFighter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        File galaxyInputFile = new File("samplegalaxy.txt");
        try {
            WeightedGraph galaxy = readGalaxy(galaxyInputFile);
            galaxy.printWeightedEdges();

            Pilot james = new Pilot("James", -1.0, false);
            Pilot ian = new Pilot("Ian", 5.0, true);
            Pilot kevin = new Pilot("Kevin", 4.0, true);
            Pilot jon = new Pilot("Jon", -1.0, false);

            List pilots = new ArrayList<Pilot>();
            pilots.add(james);
            pilots.add(ian);
            pilots.add(kevin);
            pilots.add(jon);
            System.out.print("Pilots presorted: ");
            System.out.println(pilots);
            Collections.sort(pilots);
            System.out.print("Pilots post sorted: ");
            System.out.println(pilots);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private static WeightedGraph readGalaxy(File inputFile) throws FileNotFoundException {
        try {
            Scanner input = new Scanner(inputFile);

            WeightedGraph weightedGraph = new WeightedGraph();
            ArrayList<Integer> vertices = new ArrayList<>();
            ArrayList<WeightedEdge> edges = new ArrayList<>();
            while(input.hasNextLine()) {
                String line = input.nextLine();
                String[] splitLine = line.split(" ");

                Integer v = Integer.parseInt(splitLine[0]);
                vertices.add(v);

                for(int i = 1; i < splitLine.length; i++) {
                    String[] weightedEdge = splitLine[i].split(",");
                    int u = Integer.parseInt(weightedEdge[0]);
                    double weight = Double.parseDouble(weightedEdge[1]);
                    edges.add(new WeightedEdge(u, v, weight));
                }
            }

            for (Integer v: vertices) {
                weightedGraph.addVertex(v);
            }
            for (WeightedEdge e: edges) {
                weightedGraph.addEdge(e.u, e.v, e.weight);
            }
            return weightedGraph;
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Please check filepath.");
        }
        return null;
    }

}
