/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmsproject;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author 2965, Vasileios Iakovidis, iakovidva@csd.auth.gr
 */
public class PerfectCouples {
    
    ReadFile readfile = new ReadFile();
    HashMap<Integer, ArrayList<Double>> antsmap;
    ArrayList <Integer> results = new ArrayList<>();
    
    public void start(){
        readfile.readFile();
        antsmap = readfile.getMap();
        
        for (int i=1;i<antsmap.size();i+=2){
            
            double temp = antsmap.get(i).get(3);
            int total = (int) temp;
            int [] seeds = new int [5];
            
            for (int j=0;j<5;j++){
                double temp1 = antsmap.get(i+1).get(j+3);
                seeds[j]=(int) temp1;
            }
            pasok(total,seeds,i);
        }
        eksodos();
    }
    
    private void pasok(int total, int[] seeds,int thesi){
        int [] apotelesma = new int [5];
            for (int i=0;i<5;i++){
                apotelesma[i]=0;
            }
        int T[] = new int[total + 1];
        int R[] = new int[total + 1];
        T[0] = 0;
        for(int i=1; i <= total; i++){
            T[i] = Integer.MAX_VALUE-1;
            R[i] = -1;
        }
        for(int j=0; j < seeds.length; j++){
            for(int i=1; i <= total; i++){
                if(i >= seeds[j]){
                    if (T[i - seeds[j]] + 1 < T[i]) {
                        T[i] = 1 + T[i - seeds[j]];
                        R[i] = j;
                    }
                }
            }
        }
        if (R[R.length - 1] == -1) {
            //Δεν υπάρχει πλήρης αντιστοίχηση. Συνέχεια με επόμενο ζευγάρι.
            return;
        }
        
        int start = R.length - 1;
        results.add(thesi);
        results.add(thesi+1);
        while ( start != 0 ) {
            int j = R[start];
            apotelesma[j]++;
            start = start - seeds[j];
        }
        for (int i=0;i<5;i++){
            results.add(apotelesma[i]);
        }
    }
    
    /*
    Συνάρτηση που γράφει στο αρχείο εξόδου τα τέλεια ζευγάρια μαζί με τα είδη των σπόρων που θα χρησιμοποιηθούν.
    Τα δεδομένα αποθηκεύονται σε ArrayList το οποίο έχει διαφορετικό ζευγάρι ανά 7 στοιχεία, καθώς τα πρώτα δύο είναι τα Id των μυρμηγκιών
    ενώ τα υπόλοιπα 5 είναι το πλήθος από κάθε είδος σπόρου που χρησιμοποιείται.
    */
    private void eksodos(){
            
            try
            {
            String filename = "outputC.txt";
                try (FileWriter fw = new FileWriter(filename,false)) {
                    for (int i=0;i<results.size();i+=7){
                        fw.write(results.get(i)+"\t"+results.get(i+1)+"\t"+results.get(i+2)+" "+results.get(i+3)+" "+results.get(i+4)+" "+results.get(i+5)+" "+results.get(i+6)+"\n");
                    }
                }
            }
            catch(IOException ioe){
                System.err.println("IOException: " + ioe.getMessage());
            }
        }
}
