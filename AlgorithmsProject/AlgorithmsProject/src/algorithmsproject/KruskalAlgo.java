/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmsproject;

import java.util.*;

/**
 *
 * @author vasilis
 */
public class KruskalAlgo {

    ReadFile readfile = new ReadFile();
    HashMap<Integer, ArrayList<Float>> antsmap;

    public void parsingData() {

        readfile.readFile();
        antsmap = readfile.getMap();

        Graph grafos = new Graph(antsmap.size());

        /* Emfanisi xarth
        
        antsmap.values().forEach((key) -> {
            System.out.println(key);
        });
        
         */
        // antsmap.size() = PLITHOS MURMHGIWN
        int counter = 1;
        System.out.println(antsmap.get(1).get(1));
        System.out.println(euclDist(antsmap.get(1).get(1), antsmap.get(2).get(1), antsmap.get(1).get(2), antsmap.get(2).get(2)));
        distancesneeded();
        for (int i = 1; i < antsmap.size() + 1; i++) {
            for (int j = i + 1; j < antsmap.size() + 1; j++) {
                //Edge edge = new Edge ( i, j, euclDist( antsmap.get(i).get(1),antsmap.get(j).get(1),antsmap.get(i).get(2),antsmap.get(j).get(2) ) );
                grafos.addEdge(i, j, euclDist(antsmap.get(i).get(1), antsmap.get(j).get(1), antsmap.get(i).get(2), antsmap.get(j).get(2)));
                //System.out.println(i +" "+ j +" " + euclDist( antsmap.get(i).get(1),antsmap.get(j).get(1),antsmap.get(i).get(2),antsmap.get(j).get(2) ));
                //edge.print();
            }
        }
        grafos.krustalMST();

    }

    private float euclDist(float a, float b, float c, float d) {
        float euclDist;
        euclDist = (float) Math.sqrt(Math.pow(a - b, 2) + Math.pow(c - d, 2));
        return euclDist;
    }

    private int distancesneeded() {
        int distances = 0;
        for (int i = 0; i < antsmap.size(); i++) {
            distances += i;
        }
        System.out.println("Xreiazontai: " + distances + " apostaseis");

        return distances;
    }

    class Edge {

        int parent;
        int son;
        float distance;

        Edge(int p, int s, float d) {
            parent = p;
            son = s;
            distance = d;
        }

        public void print() {
            System.out.println(parent + " - " + son + " -> " + this.distance);
        }

        public float getDist() {
            return distance;
        }

        public int getParent() {
            return parent;
        }

        public int getSon() {
            return son;
        }

        public void setParent(int a) {
            parent = a;
        }

        public void setSon(int s) {
            son = s;
        }

        public void setDist(float d) {
            distance = d;
        }

    }

    class Graph {

        int korufes;
        ArrayList<Edge> akmes = new ArrayList<>();

        Graph(int k) {
            korufes = k;
        }

        public void addEdge(int source, int destination, float weight) {
            Edge edge = new Edge(source, destination, weight);
            //edge.print();
            akmes.add(edge);

        }

        public void printall() {
            System.out.println(akmes);
        }

        public void krustalMST() {
            for (Edge num : akmes) {
                num.print();
            }
            //edw ginetai h taksinomisi 
            for (int i = 1; i < akmes.size(); i++) {
                for (int j = 1; j < akmes.size(); j++) {
                    if (akmes.get(j - 1).getDist() > akmes.get(j).getDist()) {
                        //swap
                        int temp1 = akmes.get(j - 1).getParent();
                        int temp2 = akmes.get(j - 1).getSon();
                        float temp3 = akmes.get(j - 1).getDist();

                        akmes.get(j - 1).setParent(akmes.get(j).getParent());
                        akmes.get(j - 1).setSon(akmes.get(j).getSon());
                        akmes.get(j - 1).setDist(akmes.get(j).getDist());

                        akmes.get(j).setParent(temp1);
                        akmes.get(j).setSon(temp2);
                        akmes.get(j).setDist(temp3);
                    }
                }
            }
            System.out.println("\n\n\nGAMIESAIIIIIII\n\n\n");

            for (Edge num : akmes) {
                num.print();
            }

            int[] parent = new int[antsmap.size()+1];
            makeSet(parent);

            ArrayList<Edge> mst = new ArrayList<>();
           
            int index = 0;
            int counter = 0;
            float sum=0;
            while (index < korufes - 1) {
                Edge edge = akmes.get(counter);
                int x_set = find(parent, akmes.get(counter).getParent());
                int y_set = find(parent, akmes.get(counter).getSon());
                if (x_set == y_set) {
                    //ignore, will create cycle
                } else {
                    //add it to our final result
                    mst.add(edge);
                    sum+=mst.get(index).getDist();
                    index++;
                    union(parent, x_set, y_set);

                }
                counter++;
            }
            //print MST
            System.out.println("Minimum Spanning Tree: ");
            System.out.println(sum);
            
            for (Edge num : mst) {
                num.print();
            }
        }

        public void makeSet(int[] parent) {
            for (int i = 0; i < korufes; i++) {
                parent[i] = i;
            }
        }

        public int find(int[] parent, int vertex) {
            //chain of parent pointers from x upwards through the tree
            // until an element is reached whose parent is itself
            if (parent[vertex] != vertex) {
                return find(parent, parent[vertex]);
            }
            return vertex;
        }

        public void union(int[] parent, int x, int y) {
            int x_set_parent = find(parent, x);
            int y_set_parent = find(parent, y);
            //make x as parent of y
            parent[y_set_parent] = x_set_parent;
        }
    }

}
