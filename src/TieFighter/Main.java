package TieFighter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private static final String galaxyPath = "galaxy2.txt";
    private static final String pilotPath = "pilot_routes2.txt";
    private static final String patrolsPath = "patrols.txt";

    public static void main(String[] args) {

        File galaxyInputFile = new File(galaxyPath);
        File pilotInputFile = new File(pilotPath);
        File patrolOutputFile = new File(patrolsPath);
        try {
            WeightedGraph galaxy = readGalaxy(galaxyInputFile);

            ArrayList<Pilot> pilots = readPilots(pilotInputFile, galaxy);

            writePatrols(pilots, patrolOutputFile);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private static WeightedGraph readGalaxy (File inputFile) throws FileNotFoundException {
        try {
            Scanner input = new Scanner(inputFile);

            WeightedGraph weightedGraph = new WeightedGraph();
            ArrayList<Integer> vertices = new ArrayList<>();
            ArrayList<WeightedEdge> edges = new ArrayList<>();
            while(input.hasNextLine()) {
                String line = input.nextLine();
                String[] splitLine = line.split(" ");

                Integer u = Integer.parseInt(splitLine[0]);
                vertices.add(u);

                for(int i = 1; i < splitLine.length; i++) {
                    String[] weightedEdge = splitLine[i].split(",");
                    int v = Integer.parseInt(weightedEdge[0]);
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

    private static ArrayList<Pilot> readPilots (File inputFile, WeightedGraph galaxy) {
        try {
            Scanner input = new Scanner(inputFile);

            ArrayList<Pilot> pilots = new ArrayList<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] splitLine = line.split(" ");
                String name = "";
                ArrayList<Integer> nodes = new ArrayList<Integer>();

                for (int i = 0; i < splitLine.length; i++) {
                    if (isNumeric(splitLine[i])) {
                        nodes.add(Integer.parseInt(splitLine[i]));
                    } else {
                        name += splitLine[i] + " ";
                    }
                }

                pilots.add(generatePilot(name, nodes, galaxy));
            }
            Collections.sort(pilots);
            return pilots;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * A method to check if a given String is a valid floating-point numeric
     * (double) value
     *
     * @param s     The string to be checked for numeric validity
     * @return      A boolean indicating the numeric validity of the String
     */
    public static boolean isNumeric(String s)
    {
        try
        {
            double d = Double.parseDouble(s);   // Attempts to assign string to
        }                                       // a double variable
        catch(NumberFormatException n)
        {
            return false;                       // Caught exception returns false
        }
        return true;
    }

    private static Pilot generatePilot(String name, ArrayList<Integer> nodes, WeightedGraph galaxy) {
        Boolean valid = true;
        Double length = 0.0;
        for(int i = 0; i < nodes.size() - 1; i++) {

            Integer current = nodes.get(i);
            Integer next = nodes.get(i+1);

            if (galaxy.getNeighbors(current).contains(next)) {
                for (Object o: galaxy.getEdges(current)) {
                    WeightedEdge edge = (WeightedEdge) o;
                    if (edge.v == next) {
                        length += edge.weight;
                        break;
                    }
                }
            } else {
                valid = false;
                length = -1.0;
                break;
            }
        }
        return new Pilot(name, length, valid);
    }

    public static void writePatrols(ArrayList<Pilot> pilots, File outputFile)
    {
        try
        {
            PrintWriter outputWriter = new PrintWriter(outputFile);
            for (Pilot p : pilots)
            {
                if (p.valid)
                    outputWriter.printf("%-25s%10.1f\t\t%-10s\n", p.name, p.length, "valid");
                else
                    outputWriter.printf("%-25s%10s\t\t%-10s\n", p.name, "", "invalid");
            }
            outputWriter.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("I/O Error. Please check file locations.");
        }
    }
}
