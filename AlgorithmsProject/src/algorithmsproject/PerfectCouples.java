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
    
    ArrayList <Integer> results = new ArrayList<>();
    
    public PerfectCouples(HashMap<Integer, ArrayList<Double>> antsmap){
        
        for (int i=1;i<antsmap.size();i+=2){
            
            double temp = antsmap.get(i).get(3);
            int capacity = (int) temp;
            int [] seeds = new int [5];
            
            for (int j=0;j<5;j++){
                double temp1 = antsmap.get(i+1).get(j+3);
                seeds[j]=(int) temp1;
            }
            start(capacity,seeds,i);
        }
        eksodos();
    }
    /*
    Ο πίνακας R δείχνει για κάθε θέση i ποιο στοιχείο του πίνακα seeds συμμετείχε τελευταιο για να επιτευχθεί το αντίστοιχο i.
    Ο πίνακας Τ δείχνει για κάθε θέση i, πόσοι σπόροι από τους διαθέσιμους του πίνακα seeds χρειάζονται για να συμπληρωθεί το ποσό i. 
    */
    private void start(int capacity, int[] seeds,int thesi){
        int [] apotelesma = new int [5];
        for (int i=0;i<5;i++){
            apotelesma[i]=0;
        }
        int SeedsNum[] = new int[capacity + 1];
        int LastSeed[] = new int[capacity + 1];
        SeedsNum[0] = 0;
        for(int i=1; i <= capacity; i++){
            SeedsNum[i] = Integer.MAX_VALUE-1;
            LastSeed[i] = -1;
        }
        for(int j=0; j < seeds.length; j++){
            for(int i=1; i <= capacity; i++){
                if(i >= seeds[j]){
                    if (SeedsNum[i - seeds[j]] + 1 < SeedsNum[i]) {
                        SeedsNum[i] = 1 + SeedsNum[i - seeds[j]];
                        LastSeed[i] = j;
                    }
                }
            }
        }
        if (LastSeed[LastSeed.length - 1] == -1) {
            /*
            Δεν υπάρχει πλήρης αντιστοίχηση καθώς η τιμή του τελευταίου στοιχείου στον πίνακα R έχει παραμείνει στην αρχική τιμή που της δώσαμε.
            Συνέχεια με επόμενο ζευγάρι.
            */
            return;
        }
        int start = LastSeed.length - 1;
        results.add(thesi);
        results.add(thesi+1);
        /*
        Όσο χωράει και άλλος σπόρος, αυξάνουμε την τιμή στον πίνακα των αποτελέσματων του j, δηλαδή του σπόρου που χρησιμοποιήσαμε
        και μειώνουμε το βάρος που ζητάμε τόσο όσο ο σπόρος που βάλαμε τελευταίο.
        */
        while ( start != 0 ) {
            int j = LastSeed[start];
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
