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
    
    ReadFile pasok = new ReadFile();
    
    public void panagiotis(){
        pasok.readFile();
        HashMap<Integer,ArrayList<Float>> antsmap = pasok.getMap();
        
        antsmap.values().forEach((key) -> {
            System.out.println(key);
        }); 
        
    }
    
}
