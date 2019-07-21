// Marlon Calvo
// COP3503C-19 C001, Summer 19
// NID: ma627468
//
// This class provides an implementation of Topological Sort that allows the user
// to find a Sort where they can find a Vertex "A", that could be "visited" before some
// Vertex "B".

import java.io.File;
import java.io.IOException;

import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

public class ConstrainedTopoSort
{
    private List<List<Integer>> adjList;
    private int [] dependencies;
    private int numberOfVertices;

    private static final int VISITED = -1;

    // This method needs to read in the File given and create ajdList from it.
    // It retains the order from the original List which may help with debugging.
    // Index 0 in the list is also the first element in the file.
    public ConstrainedTopoSort(String fileName)
    {
        Scanner scan = null;
        try
        {
            scan = new Scanner(new File(fileName));

        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        this.numberOfVertices = scan.nextInt();
        this.adjList = new ArrayList<>(numberOfVertices + 1);
        this.adjList.add(new ArrayList<>());

        // We are grabbing data line by line and adding to adjList.
        // Each line is a new "vertex" with a List of its adjacent vertices, a subList of adjList.
        for (int i = 0; i < this.numberOfVertices; i++)
        {
            int numberOfAdjacentVertices = scan.nextInt();
            List<Integer> adjacentVertices = new ArrayList<>(numberOfAdjacentVertices);

            for (int j = 0; j < numberOfAdjacentVertices; j++)
            {
                adjacentVertices.add(scan.nextInt());
            }

            this.adjList.add(adjacentVertices);
        }

        // We need to find all the dependencies for the List in order to see
        // which Node we can begin traversing through.
        this.dependencies = new int[this.numberOfVertices + 1];
        for (List<Integer> adjacencyForVertex : this.adjList)
        {
            for (Integer vertex : adjacencyForVertex)
            {
                dependencies[vertex]++;
            }
        }
    }

    // This method ensures that a graph contains a valid topo sort where some 
    // Vertex X is "visited" before some Vertex Y.
    // It will return true if such condition is met, and that there is a valid topo sort.
    // It will return false if such condition cannot be met, or that the topo sort is invalid.
    public boolean hasConstrainedTopoSort(int x, int y)
    {
        int [] dependencies = new int[this.dependencies.length];
        System.arraycopy(this.dependencies, 0, dependencies, 0, this.dependencies.length);

        // We must also note what nodes we have used so we do not visit Nodes
        // with 0 dependencies
        boolean gotX = false;
        boolean foundXBeforeY = false;

        // A valid topo sort includes every vertex in the graph.
        // A O(|V|) operation is performed to "visit" each vertex in a topo sort.
        // Within each iteration, we will perform two O(|V|) operations to find the next
        // node to visit and update all the dependency values for vertices that are adjacent to it.
        for (int iteration = 0; iteration < this.numberOfVertices; iteration++)
        {
            int nextVertex = Integer.MIN_VALUE;

            for (int vertex = 1; vertex <= this.numberOfVertices; vertex++)
            {
                // We want to find a "path" that leads to X, before Y.
                // This line prevents from reaching Y.
                if (dependencies[vertex] == 0 && (!gotX && y != vertex))
                {
                    // Once we have found this vertex, we are good to keep seaching
                    // for a valid TopoSort
                    if (x == vertex)
                    {
                        gotX = true;
                    }

                    // Change state to continue sort
                    nextVertex = vertex;
                    dependencies[vertex] = VISITED;
                    break;
                }

                // This line continues the topo sort to make sure we have a valid
                // final topo sort.
                else if (dependencies[vertex] == 0 && gotX)
                {
                    // We also need to make sure there is a valid path to Y.
                    if (y == vertex)
                    {
                        foundXBeforeY = true;
                    }

                    // Change state to continue sort
                    nextVertex = vertex;
                    dependencies[vertex] = VISITED;
                    break;
                }
            }

            // Means we did not find any possible sort that reaches X, before Y.
            if (nextVertex == Integer.MIN_VALUE)
            {
                return false;
            }

            // Update dependencies values for all Vertices adjacent to nextVertex
            for (Integer adjacentVertex : this.adjList.get(nextVertex))
            {
                dependencies[adjacentVertex]--;
            }
        }

        // Final test to make sure it was a valid topo sort.
        // A valid topo sort would "visit" each vertex.
        for (int i = 1; i < dependencies.length; i++)
        {
            if (dependencies[i] != VISITED)
            {
                return false;
            }
        }

        return foundXBeforeY;
    }

    public static double difficultyRating()
    {
        return 2.50;
    }

    public static double hoursSpent()
    {
        return 10.00;
    }
}
