/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmsproject;

import java.io.*;
import java.util.*;

/**
 *
 * @author 2965, Vasileios Iakovidis, iakovidva@csd.auth.gr
 */
public class KruskalAlgo {

    ReadFile readfile = new ReadFile();
    HashMap<Integer, ArrayList<Double>> antsmap;
    
    /*
    Με αυτή την συνάρτηση ξεκινάει η εκτέλεση της κλάσης για τον αλγόριθμο του Kruskal. Μέσα στο HashMap antsmap υπάρχει όλη η πληροφορία για όλα τα
    μυρμήγκια που αντλείται από την κλάση ReadFile. Η συνάρτηση start προσθέτει στο γράφο όλους τους συνδιασμούς μυρμηγκιών μαζί με την μεταξύ τους
    απόσταση και στη συνέχεια καλεί τον αλγόριθμο Kruskal.
    */
    public void start() {

        readfile.readFile();
        antsmap = readfile.getMap();
        
        Graph grafos = new Graph(antsmap.size());
        for (int i = 1; i < antsmap.size() + 1; i++) {
            for (int j = i + 1; j < antsmap.size() + 1; j++) {
                grafos.addEdge(i, j, euclDist(antsmap.get(i).get(1), antsmap.get(j).get(1), antsmap.get(i).get(2), antsmap.get(j).get(2)));
            }
        }
        grafos.krustalMST();

    }

    //  Επιστρέφει την ευκλείδια απόσταση μεταξύ δύο σημείων, δηλαδή δύο μυρμηγκιών.
    private double euclDist(double a, double b, double c, double d) {
        double euclDist;
        euclDist = (double) Math.sqrt(Math.pow(a - b, 2) + Math.pow(c - d, 2));
        return euclDist;
    }

    /*
    Κλάση Edge που αποθηκεύει όλα τα συνδεόμενα μυρμηγκια μαζί με την απόσταση τους ως ακμές.
    Η κλάση έχει τους κατάλληλους setters και getters και λειτουργεί βοηθητικά στη δημιουργία του γράφου.
    */
    class Edge {

        int parent;
        int son;
        double distance;

        Edge(int p, int s, double d) {
            parent = p;
            son = s;
            distance = d;
        }

        private void print() {
            System.out.println(parent + "  " + son);
        }

        private double getDist() {
            return distance;
        }

        private int getParent() {
            return parent;
        }

        private int getSon() {
            return son;
        }

        private void setParent(int a) {
            parent = a;
        }

        private void setSon(int s) {
            son = s;
        }

        private void setDist(double d) {
            distance = d;
        }

    }

    
    //Η κλάση Graph χειρίζεται τις ακμές που δημιουργούνται από την ένωση μεταξύ των μυρμηγκιών, χειρίζεται δηλαδή ένα ArrayList από ακμές.
    class Graph {

        int korufes;
        ArrayList<Edge> akmes = new ArrayList<>();

        Graph(int k) {
            korufes = k;
        }

        private void addEdge(int source, int destination, double weight) {
            Edge edge = new Edge(source, destination, weight);
            akmes.add(edge);

        }

        /*
        Αρχικά ταξινομούνται οι ακμές. Δημιουργείται βοηθητικός πίνακας parent και ένα καινούριο ArrayList ακμών το οποίο θα κρατήσει το 
        Minimum spanning tree. Όσο έχουμε προσθέσει ακμές λιγότερες από το πλήθος των κορυφών-1 τρέχει ο αλγόριθμος παίρνοντας διαδοχικά
        τις μικρότερες αποστάσεις. Αν δημιουργείται κύκλος η ακμή απορρίπτεται, διαφορετικά προστίθεται στο γράφο. Στο τέλος καλούμε την
        ένωση μεταξύ των δύο σημείων και αυξάνουμε το πλήθος των ακμών που έχουν μπει.
        */
        private void krustalMST() {
            
            sortakmes();
            int[] parent = new int[antsmap.size()+1];
            for (int i=0;i<korufes;i++){
                parent[i]=i;
            }

            ArrayList<Edge> mst = new ArrayList<>();
           
            int index = 0;
            int counter = 0;
            float sum=0;
            while (index < korufes - 1) {
                Edge edge = akmes.get(counter);
                int x_set = find(parent, akmes.get(counter).getParent());
                int y_set = find(parent, akmes.get(counter).getSon());
                if (x_set != y_set) {
                    mst.add(edge);
                    sum+=mst.get(index).getDist();
                    index++;
                    union(parent, x_set, y_set);
                }

                counter++;
            }
            
            int[][] mstarr = new int [korufes-1][2];
            int u = 0;
            for (Edge num : mst) {
                //num.print();
                mstarr[u][0]=num.getParent();
                mstarr[u][1]=num.getSon();
                u++;
            }
            // Ταξινόμηση στον πίνακα που θα σταλθεί στην έξοδο.
            for (int i=1;i<mstarr.length;i++){
                for (int j=1;j<mstarr.length;j++){
                    if (mstarr[j-1][0]>mstarr[j][0]){
                        int temp1=mstarr[j-1][0];
                        int temp2=mstarr[j-1][1];
                        mstarr[j-1][0]=mstarr[j][0];
                        mstarr[j-1][1]=mstarr[j][1];
                        mstarr[j][0]=temp1;
                        mstarr[j][1]=temp2;
                    }
                    else if (mstarr[j-1][0]==mstarr[j][0] && mstarr[j-1][1]>mstarr[j][1]){
                        int temp1=mstarr[j-1][0];
                        int temp2=mstarr[j-1][1];
                        mstarr[j-1][0]=mstarr[j][0];
                        mstarr[j-1][1]=mstarr[j][1];
                        mstarr[j][0]=temp1;
                        mstarr[j][1]=temp2;
                    }
                }
            }
            mst.clear(); // Δεν χρειάζεται πλέον το ArrayList mst μιας και έχουμε αποθηκεύσει τις τιμές σε πίνακα που θα σταλθεί στην έξοδο.
            eksodos(mstarr,sum);
            
        }
        // Συνάρτηση που γράφει στο αρχείο εξόδου τον πίνακα με τις ακμές και το άθροισμα του δέντρου.
        private void eksodos(int [][] mstarr,float athroisma){
            
            try
            {
            String filename = "outputA.txt";
                try (FileWriter fw = new FileWriter(filename,false)) {
                    fw.write(athroisma+"\n");
                    for (int i=0;i<korufes-1;i++){
                        
                        for (int j=0;j<2;j++){
                            fw.write(mstarr[i][j]+" ");
                        }
                        fw.write("\n");
                    }
                }
            }
            catch(IOException ioe){
                System.err.println("IOException: " + ioe.getMessage());
            }
        }
        
        /*
        Η συνάρτηση sortakmes ταξινομεί τις ακμές με βάση την απόσταση των μηρμυγκιών με τη μέθοδο της bubblesort.
        */
        private void sortakmes(){
            for (int i = 1; i < akmes.size(); i++) {
                for (int j = 1; j < akmes.size(); j++) {
                    if (akmes.get(j - 1).getDist() > akmes.get(j).getDist()) {
                        //swap
                        int temp1 = akmes.get(j - 1).getParent();
                        int temp2 = akmes.get(j - 1).getSon();
                        double temp3 = akmes.get(j - 1).getDist();

                        akmes.get(j - 1).setParent(akmes.get(j).getParent());
                        akmes.get(j - 1).setSon(akmes.get(j).getSon());
                        akmes.get(j - 1).setDist(akmes.get(j).getDist());

                        akmes.get(j).setParent(temp1);
                        akmes.get(j).setSon(temp2);
                        akmes.get(j).setDist(temp3);
                    }
                }
            }
        }
        
        private int find(int[] parent, int vertex) {
            if (parent[vertex] != vertex) {
                return find(parent, parent[vertex]);
            }
            return vertex;
        }
        
        // Η συνάρτηση union θέτει ως parent του y το x
        private void union(int[] parent, int x, int y) {
            int x_set_parent = find(parent, x);
            int y_set_parent = find(parent, y);
            parent[y_set_parent] = x_set_parent;
        }
    }
}
