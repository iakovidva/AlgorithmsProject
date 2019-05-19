/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmsproject;

/**
 *
 * @author vasilis
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadFile {
    
    Scanner scan;
    
    private HashMap<Integer, ArrayList<Float>> map = new HashMap<>();
        
    public void openFile(){
        try{
            scan = new Scanner (new File("inputA.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("Could not find file");
        }
    }
    
    public void readFile(){
        openFile();
        
        int counter = 1;
        while(scan.hasNext()){
            
            ArrayList<Float> redant = new ArrayList<>();
            String id = scan.next();
            String x = scan.next();
            String y = scan.next();
            String capacity = scan.next();
            redant.add(Float.parseFloat(id));
            redant.add(Float.parseFloat(x));
            redant.add(Float.parseFloat(y));
            redant.add(Float.parseFloat(capacity));
            map.put(counter++, redant);
            
            scan.nextLine();
            
            ArrayList<Float> blackant = new ArrayList<>();
            String id1 = scan.next();
            String x1 = scan.next();
            String y1 = scan.next();
            String a = scan.next();
            String b = scan.next();
            String c = scan.next();
            String d = scan.next();
            String e = scan.next();
            blackant.add(Float.parseFloat(id1));
            blackant.add(Float.parseFloat(x1));
            blackant.add(Float.parseFloat(y1));
            blackant.add(Float.parseFloat(a));
            blackant.add(Float.parseFloat(b));
            blackant.add(Float.parseFloat(c));
            blackant.add(Float.parseFloat(d));
            blackant.add(Float.parseFloat(e));
            map.put(counter++, blackant);
           
        }
        
/*        map.values().forEach((key) -> {
            System.out.println(key);
        });        
        
*/                
        closeFile();
    }
    
    public HashMap<Integer, ArrayList<Float>> getMap() {
         return map;
    }
    
    
    public void closeFile(){
        scan.close();
    }

    
    
}
