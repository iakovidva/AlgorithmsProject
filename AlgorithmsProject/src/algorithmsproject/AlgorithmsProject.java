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
public class AlgorithmsProject {
 
    /* 
    Εδώ δημιουργείται ο χάρτης μυρμηγκιών. Σκανάρεται το αρχείο εισόδου γραμμή προς γραμμή, στις γραμμές με περιττό αριθμό αντιστοιχούν τα κόκκινα
    μυρμήγκια, άρα χρειαζόμαστε 4 στοιχεία, Id, X, Y, Capacity ενώ στις άρτιες γραμμές τα μαύρα, όπου χρειαζόμαστε 8 στοιχεία, Id, X, Y, C1,C2,C3,C4,C5.
    */
    public static void main(String[] args) {
        
        Scanner scan;
        HashMap<Integer, ArrayList<Double>> map = new HashMap<>();
        try {
            scan = new Scanner(new File(args[0]));
            int counter = 1;
            while(scan.hasNext()){
                ArrayList<Double> redant = new ArrayList<>();
                String id = scan.next();
                String x = scan.next();
                String y = scan.next();
                String capacity = scan.next();
                redant.add(Double.parseDouble(id));
                redant.add(Double.parseDouble(x));
                redant.add(Double.parseDouble(y));
                redant.add(Double.parseDouble(capacity));
                map.put(counter++, redant);
                scan.nextLine();
                ArrayList<Double> blackant = new ArrayList<>();
                String id1 = scan.next();
                String x1 = scan.next();
                String y1 = scan.next();
                String a = scan.next();
                String b = scan.next();
                String c = scan.next();
                String d = scan.next();
                String e = scan.next();
                blackant.add(Double.parseDouble(id1));
                blackant.add(Double.parseDouble(x1));
                blackant.add(Double.parseDouble(y1));
                blackant.add(Double.parseDouble(a));
                blackant.add(Double.parseDouble(b));
                blackant.add(Double.parseDouble(c));
                blackant.add(Double.parseDouble(d));
                blackant.add(Double.parseDouble(e));
                map.put(counter++, blackant);   
            }
            scan.close();
            
            KruskalAlgo kruskal = new KruskalAlgo(map);
            GaleShapley gs = new GaleShapley(map);
            PerfectCouples pc = new PerfectCouples(map);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file");
        } 
    }
    
}
