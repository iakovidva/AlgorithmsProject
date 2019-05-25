/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmsproject;

/**
 *
 * @author 2965, Vasileios Iakovidis, iakovidva@csd.auth.gr
 */
public class AlgorithmsProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        KruskalAlgo ka = new KruskalAlgo();
        ka.start();
        GaleShapley gs = new GaleShapley();
        gs.start();
        PerfectCouples pc = new PerfectCouples();
        pc.start();
        
    }
    
}
