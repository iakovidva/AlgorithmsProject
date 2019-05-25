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
public class GaleShapley {
    
    ReadFile readfile = new ReadFile();
    HashMap<Integer, ArrayList<Double>> antsmap;
    HashMap<Integer, LinkedList <Double>> protimiseis;
    HashMap<Integer, LinkedList <Integer>> intprotimiseis;
        
    /*
    Από εδώ ξεκινάει η εκτέλεση του αλγορίθμου GaleShapley. Χρησιμοποιείται ένα HashMap το οποίο αντλείται από την κλάση readfile από όπου παίρνεται όλη
    η πληροφορία για τα μυρμήγκια. Στο HashMap protimiseis περνάνε αρχικά ως δεδομένα σε LinkedList οι αποστάσεις που έχει το κάθε μυρμήγκι με όλα τα υπόλοιπα
    με τη μορφή: μυρμήγκι1, μυρμήγκι2, απόσταση1-2, μυρμήγκι3, απόσταση 1-3. Έπειτα με τη βοήθεια της sortprotimiseis ταξινομούνται τα μυρμήγκια σύμφωνα με τις
    προτιμήσεις που έχουν στα άλλα μυρμήγκια. Μετά με την katharismos σβήνεται η περιττή πληροφορία από τις λίστες (οι αποστάσεις) καθώς χρειαζόμαστε απλά την
    προτίμηση του κάθε μυρμηγκιού και όχι και την κάθε απόσταση. Τέλος με τη συνάρτηση tairiasma βρίσκω τα ζευγάρια.
    */
    public void start() {

        readfile.readFile();
        antsmap = readfile.getMap();
        protimiseis = new HashMap<>();
        intprotimiseis = new HashMap<>();
        
        for (int i=1;i<antsmap.size()+1;i++){
            LinkedList<Double> temp = new LinkedList();
            temp.add((double)i);
            if (i%2==1){
                for (int j=2;j<antsmap.size()+1;j+=2){
                    temp.add((double)j);
                    temp.add(euclDist(antsmap.get(i).get(1), antsmap.get(j).get(1), antsmap.get(i).get(2), antsmap.get(j).get(2)));
                }
            }
            if (i%2==0){
                for (int j=1;j<antsmap.size();j+=2){
                    temp.add((double)j);
                    temp.add(euclDist(antsmap.get(i).get(1), antsmap.get(j).get(1), antsmap.get(i).get(2), antsmap.get(j).get(2)));
                }
            }
            protimiseis.put(i, temp);
        }
        sortprotimiseis();
        katharismos();
        //Αντιγραφή του HashMap protimiseis σε καινούριο HashMap με ακέραιους αριθμούς.
        for (int i=1;i<protimiseis.size()+1;i++){
            LinkedList<Integer> alist = new LinkedList<>();
            for (int j=0;j<protimiseis.get(i).size();j++){
                double temp=protimiseis.get(i).get(j);
                int add = (int) temp;
                alist.add(add);
            }
            intprotimiseis.put(i, alist);
        }
        protimiseis.clear(); // Δεν χρειάζεται πλέον το HashMap protimiseis μιας και έχουμε αποθηκεύσει τις τιμές σε νέο με ακέραιους αριθμούς.
        tairiasma();
    }

    //Συνάρτηση που πετυχαίνει το ζευγάρωμα των κόκκινων με των μαύρων μυρμηγκιών.
    private void tairiasma(){
        
        //Αρχικά προσθέτω το 0 στο τέλος κάθε μυρμηγκιού που δηλώνει ότι είναι ελεύθερα.
        for (int i=1;i<intprotimiseis.size()+1;i++){
            intprotimiseis.get(i).add(0);
        }
        
        int[][] zeugaria = new int [intprotimiseis.size()/2][2];
        int counter = 0;
        
        boolean flag = true;
        while (flag == true){
            // Ελέγχο αν υπάρχει έστω και ένα κόκκινο που να είναι ελεύθερο.
            flag = false; // Έστω ότι δεν υπάρχει κάποιο ελεύθερο
            for (int i=1;i<intprotimiseis.size();i+=2){
                int check=intprotimiseis.get(i).getLast();
                if (check==0){
                    flag=true;
                    break; // Αν βρεθεί ένα ελεύθερο μπορώ να βγω από την επανάληψη.
                }
            }
            
            // Διαλέγω ένα ένα τα κόκκινα μυρμήγκια για να κάνουν πρόταση στα μαύρα.
            for (int i=1;i<intprotimiseis.size();i+=2){
                if (intprotimiseis.get(i).getLast()==0 ){ // Ελέγχω αν κόκκινο ελεύθερο.
                    int first = intprotimiseis.get(i).get(1); // Πρώτη επιλογή του κόκκινου.
                    if (intprotimiseis.get(first).getLast()==0){ // Αν μαύρο ελεύθερο.
                        
                        intprotimiseis.get(i).set(intprotimiseis.get(i).size()-1, 1); // Θέτω το κόκκινο παντρεμένο βάζοντας 1 στο τέλος αντί για 0.
                        int thesi_antra = intprotimiseis.get(first).indexOf(i);
                        intprotimiseis.get(first).set(intprotimiseis.get(first).size()-1, thesi_antra); // Στην τελευταία θέση του μαύρου αντί για 0, βάζω την θέση που έχει στις προτιήσεις της το κόκκινο ώστε μετά να μπορώ να ελέγξω αν προτιμάει το επόμενο που θα του κάνει πρόταση.
                        zeugaria[counter][0]=i;
                        zeugaria[counter][1]=first;
                        counter++;
                    }
                    else if(intprotimiseis.get(first).getLast()!=0){ //Αν μαύρο ζευγαρωμένο.
                        int thesi_twrinou = intprotimiseis.get(first).getLast();
                        int twrinos_antras= intprotimiseis.get(first).get(thesi_twrinou);
                        int thesi_antra = intprotimiseis.get(first).indexOf(i);
                        if (thesi_antra<thesi_twrinou){ // Αν ζευγαρωμένο αλλά προτιμάει αυτό που του κάνει πρόταση από το τωρινό του κόκκινο.
                            intprotimiseis.get(i).set(intprotimiseis.get(i).size()-1, 1); // Θέτω νέο κόκκινο παντρεμένο.
                            intprotimiseis.get(first).set(intprotimiseis.get(first).size()-1, thesi_antra); // Θέτω μαύρο ζευγαρωμένο με νέο μαύρο.
                            intprotimiseis.get(twrinos_antras).set(intprotimiseis.get(twrinos_antras).size()-1, 0); //Προηγούμενο κόκκινο ξανά ελεύθερο.
                            intprotimiseis.get(twrinos_antras).removeFirst(); // Σβήνει την επιλογή του μαύρου που είχε πριν, αφού το άφησε για το νέο κόκκινο.
                        }
                        else{
                            intprotimiseis.get(i).removeFirst(); // Μαύρο ζευγαρωμένο και απορρίπτει κόκκινο.    
                        }
                    }
                }
            }
        }
        
        // Ταξινόμηση στον πίνακα που θα σταλθεί στην έξοδο.
            for (int i=1;i<zeugaria.length;i++){
                for (int j=1;j<zeugaria.length;j++){
                    if (zeugaria[j-1][0]>zeugaria[j][0]){
                        int temp1=zeugaria[j-1][0];
                        int temp2=zeugaria[j-1][1];
                        zeugaria[j-1][0]=zeugaria[j][0];
                        zeugaria[j-1][1]=zeugaria[j][1];
                        zeugaria[j][0]=temp1;
                        zeugaria[j][1]=temp2;
                    }
                }
            }
        intprotimiseis.clear(); // Δεν χρειάζεται πλέον το HashMap intprotimiseis μιας και έχουμε αποθηκεύσει τις τιμές σε νέο πίνακα zeugaria.
        eksodos(zeugaria);
    }
    
    // Συνάρτηση που γράφει στο αρχείο εξόδου τον πίνακα των ζευγαριών.
    private void eksodos(int[][] zeugaria){
        try
            {
            String filename = "outputB.txt";
                try (FileWriter fw = new FileWriter(filename,false)) {
                    for (int i=0;i<zeugaria.length;i++){
                        
                        for (int j=0;j<2;j++){
                            fw.write(zeugaria[i][j]+" ");
                        }
                        fw.write("\n");
                    }
                }
            }
            catch(IOException ioe){
                System.err.println("IOException: " + ioe.getMessage());
            }
    }
    //Σβήνει περιττή πληροφορία (αποστάσεις) από το hashmap protimiseis.
    private void katharismos(){   
        for (int i=1;i<protimiseis.size()+1;i++){
            int epanalipseis = protimiseis.get(i).size();
            for (int j=epanalipseis-1;j>1;j-=2){
                protimiseis.get(i).remove(j);
            }
        }
    }
    //Ταξινομεί το hashmap protimiseis σύμφωνα με τις αποστάσεις.
    private void sortprotimiseis(){
        double temp,temp1;
        for (int i=1;i<protimiseis.size()+1;i++){   
            for (int j=2;j<protimiseis.size()+1;j+=2){
                for (int u=j+2;u<protimiseis.size()+1;u+=2){
                    if ((protimiseis.get(i).get(j))>(protimiseis.get(i).get(u))){
                        temp=protimiseis.get(i).get(u-1);
                        temp1=protimiseis.get(i).get(u);
                        protimiseis.get(i).set(u-1, protimiseis.get(i).get(j-1));
                        protimiseis.get(i).set(u, protimiseis.get(i).get(j));
                        protimiseis.get(i).set(j-1, temp);
                        protimiseis.get(i).set(j,temp1);               
                    }
                }
            }
        }
    }
    //  Επιστρέφει την ευκλείδια απόσταση μεταξύ δύο σημείων, δηλαδή δύο μυρμηγκιών.
    private double euclDist(double a, double b, double c, double d) {
        double euclDist;
        euclDist = (double) Math.sqrt(Math.pow(a - b, 2) + Math.pow(c - d, 2));
        return euclDist;
    }
}
