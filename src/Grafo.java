
import edu.princeton.cs.algs4.StdOut;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Grafo {

    public static RedBlackBST2<Integer, Cache> cachesGrafosST = new RedBlackBST2<>();
    public static RedBlackBST2<Integer, Cache> cachesGrafosPremiumST = new RedBlackBST2<>();

    public ArrayList<Integer> verticesToAvoid = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        Main.readInputFromFile();
        // Cache.printCachesST();

       /* EdgeWeightedDigraphNew graphManual = new EdgeWeightedDigraphNew(Cache.cachesST.size());
        DirectedEdgeNew e1 = new DirectedEdgeNew(1, 6, 67, 10.1);
        DirectedEdgeNew e2 = new DirectedEdgeNew(12, 6, 5, 13);
        DirectedEdgeNew e3 = new DirectedEdgeNew(12, 2, 7, 1.01);
        DirectedEdgeNew e4 = new DirectedEdgeNew(8, 3, 10, 2.5);
        DirectedEdgeNew e5 = new DirectedEdgeNew(3, 8, 32.5, 100000.0);

        graphManual.addEdge(e1);
        graphManual.addEdge(e2);
        graphManual.addEdge(e3);
        graphManual.addEdge(e4);
        graphManual.addEdge(e5);

        System.out.println(graphManual);*/

        grafoGeral();
        //grafoPremium();

    }

    /**
     * lê de ficheiro e cria o grafo geral
     */
    public static void grafoGeral() {

        SymbolDigraphNew sd = new SymbolDigraphNew("data/grafoIDs.txt", ";");
        EdgeWeightedDigraphNew graph = sd.digraph();

        Integer count = 0;

        for (Integer i : Cache.cachesST.keys()) {
            Cache c = Cache.cachesST.get(i);
            cachesGrafosST.put(count, c);
            count++;
        }
        for (int j : cachesGrafosST.keys()) {
            System.out.println("Nó " + j + " -> " + cachesGrafosST.get(j));
        }
        System.out.println();
        System.out.println(graph);

        //SORTEST PATH
        System.out.println("Shortest Path From one vertice to another: ");
        shortestPath(0, 17, graph);

        //SHORTEST PATH TO ALL
        System.out.println("Shortest Path To All Vertices: ");
        shortestPathToAll(graph, 7);

    }

    /**
     * lê de ficheiro e cria o cria o grafo com as caches premium
     */
    public static void grafoPremium() {

        SymbolDigraphNew sd = new SymbolDigraphNew("data/grafoIDsPremium.txt", ";");
        EdgeWeightedDigraphNew graphPremium = sd.digraph();

        for (int cacheID : Cache.cachesST.keys()) {
            if (Cache.cachesST.get(cacheID).getTipo().equals(Tipo.PREMIUM)) {
                cachesGrafosPremiumST.put(cacheID, Cache.cachesST.get(cacheID));
            }
        }

        for (int j : cachesGrafosPremiumST.keys()) {
            System.out.println("Nó " + j + " -> " + cachesGrafosPremiumST.get(j));
        }

        System.out.println();
        System.out.println(graphPremium);

        //SORTEST PATH
        System.out.println("Shortest Path From one vertice to another: ");
        shortestPath(0, 7, graphPremium);

        //SHORTEST PATH TO ALL
        System.out.println("Shortest Path To All Vertices; ");
        shortestPathToAll(graphPremium, 7);

    }

    /**
     * Calcula o caminho mais curto entre o vértice1 e o vertice2
     * @param vertice1 cache inicial
     * @param vertice2 cache2 final
     * @param ed grafo edge weighted
     */
    public static void shortestPath(int vertice1, int vertice2, EdgeWeightedDigraphNew ed) {
        //type1 = Distancia  Type2 = Tempo
        DijkstraSPNew dijkstra = new DijkstraSPNew(ed, vertice1, 1);

        if (dijkstra.hasPathTo(vertice2)) {
            StdOut.printf("From: %d to %d (%.2f) \n", vertice1, vertice2, dijkstra.distTo(vertice2));
            for (DirectedEdgeNew den : dijkstra.pathTo(vertice2)) {
                StdOut.print(den + "   ");
            }
            StdOut.println();
        }
        else {
            StdOut.printf("%d to %d  no path\n", vertice1, vertice2);
        }

        System.out.println("\n");
    }

    /**
     * Calcula o caminho mais curto de uma cache para todas as outras
     * @param ed grafo edge weighted
     * @param vertice cache de inicio
     */
    public static void shortestPathToAll(EdgeWeightedDigraphNew ed, int vertice) {
        //type1 = Distancia  Type2 = Tempo
        DijkstraSPNew dijkstra = new DijkstraSPNew(ed, vertice, 2);

        for (int i = 0; i < ed.V(); i++) {
            if (i == vertice) {
                StdOut.printf("%d to %d -> Has No Path\n", vertice, i);
            } else if (dijkstra.hasPathTo(i)) {
                StdOut.printf("From %d To %2d -> Final (%4f)   ", vertice, i, dijkstra.distTo(i));
                for (DirectedEdgeNew d : dijkstra.pathTo(i)) {
                    StdOut.print(d + "  ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d -> Has No Path\n", vertice, i);
            }
        }
        System.out.println("\n");
    }
}
